package com.example.repository

import com.example.dao.UserDao
import com.example.database.table.Stages
import com.example.database.table.Users
import com.example.entities.UserEntity
import com.example.model.InputData
import com.example.model.User
import com.example.utils.appConstants.GlobalConstants
import com.example.utils.helperFunctions.resultRowToUser
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepository:UserDao {

    override suspend fun createUser(user:InputData): UUID {
        return transaction {
            val userId = UserEntity.new {
                name=user.name
                email = user.email
            }.id.value
            val uuid=UUID.fromString(userId.toString())
            Stages.insert{
                it[Stages.userId]=uuid
                it[stageName]=GlobalConstants.numbersMap[1]!!
            }
            uuid
        }
    }

    override suspend fun getUserById(userId: UUID): User? {
        return transaction {
            val result = Users.select { Users.id eq userId }
                .singleOrNull()

            result?.let {
                resultRowToUser(result)
            }
        }
    }

    override suspend fun updateStage(userId: String, nextStage: Int) {
        val userUUID = UUID.fromString(userId)
        transaction {
            Users.update({ Users.id eq userUUID }) {
                it[currentStage] = nextStage
                it[Users.nextStage] = nextStage + 1
                it[lastStageUpdate]= System.currentTimeMillis()
            }
            Stages.update({ Stages.userId eq userUUID }){
                it[stageName]=GlobalConstants.numbersMap[nextStage]!!
            }
        }

    }

    override suspend fun updateIsVerified(userId: String, isVerified:Boolean){
        val userUUID = UUID.fromString(userId)
        transaction {
            Users.update({Users.id eq userUUID}){
                it[Users.isVerified]=isVerified
            }
        }
    }

    override suspend fun isExpired(userId: String,time:Long):Boolean{
        val userUUID = UUID.fromString(userId)
        val result=time-getUserById(userUUID)!!.lastStageUpdate
        return result>GlobalConstants.EXPIRETIME

    }
    override suspend fun resetStage(userId:String){
        val userUUID = UUID.fromString(userId)
        transaction {
            Users.update({Users.id eq userUUID}){
                it[currentStage]=1
                it[nextStage]=2
                it[isVerified]=false
                it[lastStageUpdate]= System.currentTimeMillis()
            }
            Stages.update({ Stages.userId eq userUUID }){
                it[stageName]=GlobalConstants.numbersMap[1]!!
            }
        }
    }
}

