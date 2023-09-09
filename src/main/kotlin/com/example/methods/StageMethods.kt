package com.example.methods

import com.example.services.*
import com.example.services.verficationServices.*
import com.example.utils.appConstants.GlobalConstants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StageMethods:KoinComponent {

    private val mobileVerificationService by inject<MobileVerificationService>()
    private val emailVerificationService by inject<EmailVerificationService>()
    private val mpinVerificationService by inject<MPINVerificationService>()
    private val aadhaarVerificationService by inject<AadhaarVerificationService>()
    private val panVerificationService by inject<PANVerificationService>()
    private val twoFactorAuthenticationService by inject<TwoFactorAuthenticationService>()

    fun isStageCompleted(stageNumber: Int): Boolean {

        return when (stageNumber) {
            1 -> mobileVerificationService.verifyMobileNumber(GlobalConstants.PHONENUMBER)

            2 -> emailVerificationService.verifyEmail(GlobalConstants.EMAIL)

            3 -> mpinVerificationService.verifyMPIN(GlobalConstants.MPIN)

            4 -> aadhaarVerificationService.verifyAadhaarNumber(GlobalConstants.AADHAR)

            5 -> panVerificationService.verifyPANCard(GlobalConstants.PAN)

            6 -> twoFactorAuthenticationService.verify2FA(GlobalConstants.TWOFA)

            else->  false
        }
    }
}