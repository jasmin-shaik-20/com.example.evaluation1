package com.example.services

import com.example.exceptions.InvalidLengthException
import com.example.exceptions.UserNotFoundException
import com.example.model.InputData
import com.example.model.User
import com.example.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class UserServices : KoinComponent  {

    private val userRepository by inject<UserRepository>()

    suspend fun handleGetUser(userId:UUID):User{
        return userRepository.getUserById(userId)?:throw UserNotFoundException()
    }

    suspend fun handlePostUser(user:InputData,
                               nameMinLength:Int?,
                               nameMaxLength:Int?,
                               emailMinLength:Int?,
                               emailMaxLength:Int?):UUID {
        if (user.name.length in nameMinLength!!..nameMaxLength!! &&
            user.email.length in emailMinLength!!..emailMaxLength!!) {
            return userRepository.createUser(user)
        } else {
            throw InvalidLengthException()
        }
    }

}