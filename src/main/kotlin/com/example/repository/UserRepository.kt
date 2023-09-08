package com.example.repository

import com.example.dao.UserDao
import com.example.database.table.Stages
import com.example.database.table.Users
import com.example.entities.StageEntity
import com.example.entities.UserEntity
import com.example.model.InputData
import com.example.model.User
import com.example.utils.helperFunctions.resultRowToUser
import io.ktor.util.date.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepository:UserDao {
    private val numbersMap = mapOf(1 to "MobileNumber", 2 to "Gmail", 3 to "MPIN", 4 to "Aadhar",5 to "Pan Card",6 to "Set 2FA")
    override suspend fun createUser(user:InputData): UUID {
        return transaction {
            val userId = UserEntity.new {
                name=user.name
                email = user.email
            }.id.value
            val uuid=UUID.fromString(userId.toString())
            Stages.insert{
                it[Stages.userId]=uuid
                it[Stages.stageName]=numbersMap[1]!!
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
                it[Users.currentStage] = nextStage
                it[Users.nextStage] = nextStage + 1
                it[Users.lastStageUpdate]= System.currentTimeMillis()
            }
            Stages.update({ Stages.userId eq userUUID }){
                it[Stages.stageName]=numbersMap[nextStage]!!
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
        println(result)
        return result>15000

    }
    override suspend fun resetStage(userId:String){
        val userUUID = UUID.fromString(userId)
        transaction {
            Users.update({Users.id eq userUUID}){
                it[Users.currentStage]=1
                it[nextStage]=2
                it[isVerified]=false
                it[lastStageUpdate]= System.currentTimeMillis()
            }
            Stages.update({ Stages.userId eq userUUID }){
                it[Stages.stageName]=numbersMap[1]!!
            }
        }
    }
}

