package com.example.methods

import com.example.model.User
import com.example.repository.UserRepository
import io.ktor.util.date.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserMethods:KoinComponent {

    private val stageMethods by inject<StageMethods>()
    private val userRepository by inject<UserRepository>()

    suspend fun completeCurrentStage(user: User): String {
        val currentStage = user.currentStage
        val nextStage = user.nextStage
        val isCurrentStageCompleted = stageMethods.isStageCompleted(currentStage)
        val currentTime = System.currentTimeMillis()
        val isTimeExpired = userRepository.isExpired(user.id, currentTime)

        if (isCurrentStageCompleted && !isTimeExpired) {
            if (currentStage < 6) {
                userRepository.updateStage(user.id, nextStage)
                return "Stage completed successfully"
            } else if (currentStage == 6) {
                userRepository.updateIsVerified(user.id, true)
                return "Verification completed"
            }
        }
        userRepository.resetStage(user.id)
        return if (isTimeExpired) {
            "Stage reset to 1 due to inactivity"
        } else {
            "Stage not completed"
        }
    }

}