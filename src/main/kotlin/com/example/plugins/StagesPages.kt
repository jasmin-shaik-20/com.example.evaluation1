package com.example.plugins

import com.example.exceptions.UserNotFoundException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages(){
    install(StatusPages){
        exception<Throwable> { call, cause ->
            when (cause) {
                is UserNotFoundException -> call.respond("User not found")
            }
        }
    }
}