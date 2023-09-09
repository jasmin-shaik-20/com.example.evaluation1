package com.example.routes

import com.example.config.UserConfig.nameMaxLength
import com.example.config.UserConfig.nameMinLength
import com.example.config.UserConfig.emailMaxLength
import com.example.config.UserConfig.emailMinLength
import com.example.methods.UserMethods
import com.example.model.InputData
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
                val userId = userServices.handlePostUser(data, nameMinLength, nameMaxLength, emailMinLength,
                    emailMaxLength)
                call.respond(HttpStatusCode.Created, "User created with ID: $userId")
                call.application.environment.log.info("Created a new userId:$userId")
            }

            get("{id}") {
                val userId = call.parameters["id"]?.let(UUID::fromString)?:return@get call.respond("Missing id")
                val getData=userServices.handleGetUser(userId)
                call.respond(getData)
                call.application.environment.log.info("Returned user with ID: $getData")

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


