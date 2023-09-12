package com.example.plugins

import com.example.exceptions.InvalidLengthException
import com.example.exceptions.UserAlreadyExistException
import com.example.exceptions.UserNotFoundException
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages(){
    install(StatusPages){
        exception<Throwable> { call, cause ->
            when (cause) {
                is UserNotFoundException -> call.respond("User not found")
                is InvalidLengthException -> call.respond("Invalid length of username and email")
                is UserAlreadyExistException -> call.respond("A user with the same name or email already exists.")
                is RequestValidationException -> call.respondText("${cause.message}")
            }
        }
    }
}