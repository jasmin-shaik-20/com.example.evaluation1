package com.example.routes

import com.example.methods.UserMethods
import com.example.model.InputData
import com.example.model.User
import com.example.repository.UserRepository
import com.example.services.UserServices
import com.example.utils.appConstants.ApiEndPoints
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.configureUserRoutes(){
    routing {
        route(ApiEndPoints.USER) {

            val userServices:UserServices by inject()
            val userMethods:UserMethods by inject()

            post {
                val data=call.receive<InputData>()
                val userId = userServices.handlePostUser(data)
                call.respond(HttpStatusCode.Created, "User created with ID: $userId")
            }

            get("{id}") {
                val userId = call.parameters["id"]?.let(UUID::fromString)?:return@get call.respond("Missing id")
                val getData=userServices.handleGetUser(userId)
                call.respond(getData)

            }
            post("{id}/updateStage") {
                val userId = call.parameters["id"]?.let(UUID::fromString)?:return@post call.respond("Missing id")
                val user=userServices.handleGetUser(userId)
                val response=userMethods.completeCurrentStage(user)
                call.respond(response)
            }
        }
    }
}


