package com.example.routes

import com.example.model.User
import com.example.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureUserRoutes(){
    routing {
        route("/users") {
            val userRepository=UserRepository()
            post {
                val newUser = call.receive<User>()
                val userId = userRepository.createUser(newUser)
                call.respond(HttpStatusCode.Created, "User created with ID: $userId")
            }


            get("{id}") {
                val userId = call.parameters["id"]?.let(UUID::fromString)
                if (userId != null) {
                    val user = userRepository.getUser(userId)
                    if (user != null) {
                        call.respond(user)
                    } else {
                        throw NotFoundException()
                    }
                } else {
                    throw InvalidBodyException("Invalid user ID")
                }
            }
            post("{id}/updateStage") {
                val userId = call.parameters["id"]?.let(UUID::fromString)
                if (userId != null) {
                    val user = userRepository.getUser(userId)
                    if (user != null) {
                        val response = userRepository.completeCurrentStage(user)
                        call.respond(response)
                    } else {
                        throw NotFoundException()
                    }
                } else {
                    throw InvalidBodyException("Invalid user ID")
                }
            }
        }
    }
        }


