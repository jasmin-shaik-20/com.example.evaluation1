package com.example.services

class MobileVerificationService {
    fun verifyMobileNumber(mobileNumber: String): Boolean{
        if (mobileNumber.isNullOrBlank()) {
            return false
        }

        if (!mobileNumber.all { it.isDigit() }) {
            return false
        }

        val desiredLength = 10
        return mobileNumber.length == desiredLength
    }
}