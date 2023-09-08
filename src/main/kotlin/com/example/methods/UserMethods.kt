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
        val currentTime= System.currentTimeMillis()
        val time=userRepository.isExpired(user.id,currentTime)
        println("Time:$time")
        println(isCurrentStageCompleted)
        if (isCurrentStageCompleted && !time) {
            if (currentStage < 6) {
                userRepository.updateStage(user.id, nextStage)
                return "Stage completed successfully"
            } else if (currentStage == 6) {
                userRepository.updateIsVerified(user.id, true)
                return "Verification completed"
            }
            else{
                return "Stage not completed"
            }
        }
//        else if(isCurrentStageCompleted==false && currentStage>6){
//            return "Verification completed"
//        }
        else {
            userRepository.resetStage(user.id)
//            val elapsedMinutes =  (currentTime - lastStageUpdate) / (60 * 1000)

//                user.currentStage = 1
//                user.nextStage = 2
//                user.isVerified = false
//                user.lastStageUpdate = getTimeMillis()

                return "Stage reset to 1 due to inactivity"

        }
    }
}