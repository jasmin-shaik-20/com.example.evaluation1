package com.example.plugins

import com.example.utils.appConstants.ApiEndPoints
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configureCallLogging(){
    install(CallLogging){
        level= Level.INFO
        filter { call ->
            call.request.path().startsWith(ApiEndPoints.USER)
        }
    }
}