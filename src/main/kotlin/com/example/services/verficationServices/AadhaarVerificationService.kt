package com.example.services.verficationServices

class AadhaarVerificationService {
    fun verifyAadhaarNumber(aadhaarNumber: String): Boolean {

        val desiredLength = 12

        return aadhaarNumber.isNotBlank() &&
                aadhaarNumber.all { it.isDigit() } &&
                aadhaarNumber.length == desiredLength
    }
}