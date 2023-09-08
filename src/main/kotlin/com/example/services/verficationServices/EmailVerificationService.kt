package com.example.services.verficationServices


class EmailVerificationService{
    fun verifyEmail(email: String): Boolean {
        if (email.isBlank()) {
            return false
        }
        return email.contains("@") && email.contains(".")
    }

}