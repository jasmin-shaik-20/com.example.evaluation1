package com.example.methods

import com.example.model.User
import com.example.repository.UserRepository
import io.ktor.util.date.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserMethods:KoinComponent {

    private val stageMethods=StageMethods()
    private val userRepository by inject<UserRepository>()

    suspend fun completeCurrentStage(user: User): String {
        val currentStage = user.currentStage
        val nextStage = user.nextStage
        val isCurrentStageCompleted = stageMethods.isStageCompleted(currentStage)
        if (isCurrentStageCompleted) {
            userRepository.updateStage(user.id,nextStage)

            if (nextStage == 6) {
                userRepository.updateIsVerified(user.id,true)
            }

            return "Stage completed successfully"
        } else {
            val lastStageUpdate = user.lastStageUpdate
            val currentTime = getTimeMillis()
            val elapsedMinutes =  (currentTime - lastStageUpdate) / (60 * 1000)

            return if (elapsedMinutes >= 15) {
                user.currentStage = 1
                user.nextStage = 2
                user.isVerified = false
                user.lastStageUpdate = getTimeMillis()

                "Stage reset to 1 due to inactivity"
            } else {
                "Current stage not completed yet"
            }
        }
    }
}