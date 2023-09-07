package com.example.services


class EmailVerificationService{
    fun verifyEmail(email: String): Boolean {
        if (email.isNullOrBlank()) {
            return false
        }
        return email.contains("@") && email.contains(".")
    }

}