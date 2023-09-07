package com.example.model

import java.time.Instant
import java.util.*

data class User(
    val id: UUID,
    val name: String,
    val email: String,
    var currentStage: Int = 1,
    var nextStage: Int = 2,
    var isVerified: Boolean = false,
    var lastStageUpdate: Instant = Instant.now() // Add this field for tracking stage update time
)
