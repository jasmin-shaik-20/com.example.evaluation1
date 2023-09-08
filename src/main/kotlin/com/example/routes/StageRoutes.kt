package com.example.routes

import com.example.model.InputStage
import com.example.services.StageServices
import com.example.utils.appConstants.ApiEndPoints
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

//fun Application.configureStageRoutes(){
//    routing {
//        route(ApiEndPoints.STAGE){
//
//            val stageServices:StageServices by inject()
//            post{
//                val stage=call.receive<InputStage>()
//                val result=stageServices.handlePostStage(stage)
//                call.respond(HttpStatusCode.Created,"Stage is created:$result")
//            }
//        }
//    }
//}