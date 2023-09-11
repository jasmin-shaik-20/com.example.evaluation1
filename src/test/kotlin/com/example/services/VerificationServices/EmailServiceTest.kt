package com.example.services.VerificationServices

import com.example.services.verficationServices.EmailVerificationService
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EmailServiceTest {

    private val emailVerificationService=EmailVerificationService()

    //valid email
    @Test
    fun testValidEmail(){
        val email="jasmin123@gmail.com"
        assertTrue(emailVerificationService.verifyEmail(email),"valid email should return true")
    }

    //Invalid email
    @Test
    fun testInvalidEmail(){
        val email="jas123"
        assertFalse(emailVerificationService.verifyEmail(email),"Invalid email should return false")
    }
}