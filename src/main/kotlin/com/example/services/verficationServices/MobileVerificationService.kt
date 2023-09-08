package com.example.services.verficationServices

class MobileVerificationService {
    fun verifyMobileNumber(mobileNumber: String): Boolean {
        val desiredLength = 10

        return mobileNumber.isNotBlank() &&
                mobileNumber.all { it.isDigit() } &&
                mobileNumber.length == desiredLength
    }
}