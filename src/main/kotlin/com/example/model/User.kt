package com.example.model

import io.ktor.util.date.*
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.*
@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    var currentStage: Int=1,
    var nextStage: Int=currentStage+1,
    var isVerified: Boolean = false,
    var lastStageUpdate: Long= getTimeMillis()
)
