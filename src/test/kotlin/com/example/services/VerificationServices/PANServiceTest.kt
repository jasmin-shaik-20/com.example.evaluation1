package com.example.services.VerificationServices

import com.example.services.verficationServices.PANVerificationService
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PANServiceTest {

    private val panVerificationService = PANVerificationService()

    //valid PAN Card
    @Test
    fun testValidPANCard() {
        val validPAN = "ABCDE1234F"
        assertTrue(panVerificationService.verifyPANCard(validPAN), "Valid PAN should return true")
    }


    //Invalid PAN Card
    @Test
    fun testInvalidPANCard(){
        val invalidPAN1 = "ABCDE12345"
        val invalidPAN2 = "ABCD1234FG"
        val invalidPAN3 = "abcde1234f"
        assertFalse(panVerificationService.verifyPANCard(invalidPAN1), "Invalid length PAN should return false")
        assertFalse(panVerificationService.verifyPANCard(invalidPAN2), "Invalid characters PAN should return false")
        assertFalse(panVerificationService.verifyPANCard(invalidPAN3), "Lowercase characters PAN should return false")
    }

}
