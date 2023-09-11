package com.example.services.verficationServices

class TwoFactorVerificationService {

    fun verify2FA(code: String): Boolean {

        val desiredLength = 6

        return code.isNotBlank() &&
                code.all { it.isDigit() } &&
                code.length == desiredLength
    }
}