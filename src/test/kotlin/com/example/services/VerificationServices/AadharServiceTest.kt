package com.example.services.VerificationServices

import com.example.services.verficationServices.AadhaarVerificationService
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AadharServiceTest {

    private val aadhaarVerificationService=AadhaarVerificationService()

    //valid Aadhar Number
    @Test
    fun testValidAadharNumber(){
        val aadharNumber="675436789054"
        assertTrue(aadhaarVerificationService.verifyAadhaarNumber(aadharNumber),"Valid aadhar should return true")
    }

    //Invalid Aadhar Number
    @Test
    fun testInvalidAadharNumber(){
        val aadharNumber1="67853467"
        val aadharNumber2="5rt8ji9043"
        assertFalse(aadhaarVerificationService.verifyAadhaarNumber(aadharNumber1),"Invalid length should return false")
        assertFalse(aadhaarVerificationService.verifyAadhaarNumber(aadharNumber2),"Lowercase characters should return false")
    }
}