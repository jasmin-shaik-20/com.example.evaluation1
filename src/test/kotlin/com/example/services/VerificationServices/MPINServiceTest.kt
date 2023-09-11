package com.example.services.VerificationServices

import com.example.services.verficationServices.MPINVerificationService
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MPINServiceTest {

    private val mpinVerificationService=MPINVerificationService()

    //valid MPIN
    @Test
    fun testValidMPIN(){
        val MPIN="567832"
        assertTrue(mpinVerificationService.verifyMPIN(MPIN),"Valid MPIN should return true")
    }

    //Invalid MPIN
    @Test
    fun testInvalidMPIN(){
        val MPIN="123"
        assertFalse(mpinVerificationService.verifyMPIN(MPIN),"Invalid length should return false")
    }
}