package com.example.services.VerificationServices

import com.example.services.verficationServices.TwoFactorVerificationService
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TwoFactorServiceTest {

    private val twoFactorVerificationService=TwoFactorVerificationService()

    //valid 2FA
    @Test
    fun testValidTwoFactor(){
        val twoFactor="678543"
        assertTrue(twoFactorVerificationService.verify2FA(twoFactor),"Valid TwoFactor should return true")
    }

    //Invalid 2FA
    @Test
    fun testInvalidTwoFactor(){
        val twoFactor="123"
        assertFalse(twoFactorVerificationService.verify2FA(twoFactor),"Invalid length should return false")
    }
}