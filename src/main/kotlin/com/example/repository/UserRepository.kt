package com.example.repository

import com.example.dao.UserDao
import com.example.database.table.Users
import com.example.model.InputData
import com.example.model.User
import com.example.utils.helperFunctions.resultRowToUser
import io.ktor.util.date.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserRepository:UserDao {

    override suspend fun createUser(user:InputData): UUID {
        return transaction {
            val userId = Users.insertAndGetId {
                it[Users.name]=user.name
                it[Users.email] = user.email


            }.value
            UUID.fromString(userId.toString())
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
}

