package com.example.services.VerificationServices

import com.example.services.verficationServices.MobileVerificationService
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MobileServiceTest {

    private val mobileVerificationService=MobileVerificationService()

    //valid PhoneNumber
    @Test
    fun testValidPhoneNumber(){
        val phoneNumber="9390547915"
        assertTrue(mobileVerificationService.verifyMobileNumber(phoneNumber),"Valid phoneNumber should return true")
    }

    //Invalid PhoneNumber
    @Test
    fun testInvalidPhoneNumber(){
        val phoneNumber1="890564"
        val phoneNumber2="32df6785h"
        assertFalse(mobileVerificationService.verifyMobileNumber(phoneNumber1),"Invalid length should return false")
        assertFalse(mobileVerificationService.verifyMobileNumber(phoneNumber2),"Lowercase characters number should return false")
    }

}