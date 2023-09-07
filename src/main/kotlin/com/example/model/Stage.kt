package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Stage(
    val orderId: Int,
    val stageName: String
)

