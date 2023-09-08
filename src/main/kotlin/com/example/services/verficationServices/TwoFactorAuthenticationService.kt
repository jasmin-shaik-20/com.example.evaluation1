package com.example.services.verficationServices

class TwoFactorAuthenticationService {

    fun verify2FA(code: String): Boolean {

        val desiredLength = 6

        return code.isNotBlank() &&
                code.all { it.isDigit() } &&
                code.length == desiredLength
    }
}