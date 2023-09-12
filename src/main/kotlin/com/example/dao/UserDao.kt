package com.example.dao

import com.example.model.InputData
import com.example.model.User
import java.util.*

interface UserDao {
    suspend fun createUser(user: InputData): UUID

    suspend fun getUserById(userId: UUID): User?

    suspend fun updateStage(userId: String,nextStage:Int)
    suspend fun getUserByNameOrEmail(name: String,email:String): Boolean

    suspend fun updateIsVerified(userId: String, isVerified:Boolean)

    suspend fun isExpired(userId: String,time:Long):Boolean

    suspend fun resetStage(userId:String)
}