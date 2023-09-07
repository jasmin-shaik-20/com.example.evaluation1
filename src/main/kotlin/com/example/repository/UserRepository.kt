package com.example.repository

import com.example.database.table.Stages
import com.example.database.table.Users
import com.example.model.User
import com.example.services.EmailVerificationService
import com.example.services.MobileVerificationService
import io.ktor.http.*
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.util.*

class UserRepository {
    private val mobileVerificationService = MobileVerificationService()
    private val emailVerificationService = EmailVerificationService()


    fun createUser(user: User): UUID {
        return transaction {
            val userId = Users.insertAndGetId {
                it[Users.name] = user.name
                it[Users.email] = user.email
            }.value
            UUID.fromString(userId.toString())
        }
    }

    fun getUser(userId: UUID): User? {
        return transaction {
            val result = Users.select { Users.id eq userId }
                .singleOrNull()

            result?.let {
                User(
                    id = result[Users.id].value,
                    name = result[Users.name],
                    email = result[Users.email],
                    currentStage = result[Users.currentStage],
                    nextStage = result[Users.nextStage],
                    isVerified = result[Users.isVerified],
                )
            }
        }
    }

    fun completeCurrentStage(user: User): String {
        val currentStage = user.currentStage
        val nextStage = user.nextStage
        val isCurrentStageCompleted = isStageCompleted(user, currentStage)

        if (isCurrentStageCompleted) {
            user.currentStage = nextStage
            user.nextStage = nextStage + 1

            if (nextStage == 6) {
                user.isVerified = true
            }

            return "Stage completed successfully"
        } else {
            val lastStageUpdate = user.lastStageUpdate
            val currentTime = Instant.now()
            val elapsedMinutes = java.time.Duration.between(lastStageUpdate, currentTime).toMinutes()

            return if (elapsedMinutes >= 15) {
                user.currentStage = 1
                user.nextStage = 2
                user.isVerified = false
                user.lastStageUpdate = Instant.now()

                "Stage reset to 1 due to inactivity"
            } else {
                "Current stage not completed yet"
            }
        }
    }

    fun isStageCompleted(user: User, stageNumber: Int): Boolean {
        when (stageNumber) {
            1 ->
                return mobileVerificationService.verifyMobileNumber("9390547915")

            2 ->
                return emailVerificationService.verifyEmail("jasmin@gmail.com")
            else->

                return false


        }

    }
}

