package com.example.services.verficationServices

class PANVerificationService {
    fun verifyPANCard(panNumber: String): Boolean {

        val panRegex = Regex("[A-Z]{5}[0-9]{4}[A-Z]{1}")

        return panNumber.isNotBlank() && panRegex.matches(panNumber)
    }
}