package com.example.plugins

import com.example.model.InputData
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun Application.configureValidation(){
    install(RequestValidation){
        validate<InputData> { bodyText -> validateUser(bodyText) }
    }
}

private fun validateUser(bodyText: InputData): ValidationResult {
    return when {
        bodyText.name.isBlank() -> ValidationResult.Invalid("Name should not be empty")
        !bodyText.name.matches(Regex("[a-zA-Z]+")) -> ValidationResult.Invalid("Name should contain alphabetic")
        bodyText.email.isBlank() -> ValidationResult.Invalid("Email should not be empty")
        !bodyText.email.matches(Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) ->
            ValidationResult.Invalid("Invalid email address")
        else -> ValidationResult.Valid
    }
}