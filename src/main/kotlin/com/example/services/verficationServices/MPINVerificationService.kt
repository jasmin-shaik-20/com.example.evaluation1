package com.example.services.verficationServices

class MPINVerificationService {
    fun verifyMPIN(mpin: String): Boolean {
        val desiredLength = 6

        return mpin.isNotBlank() &&
                mpin.all { it.isDigit() } &&
                mpin.length == desiredLength
    }
}