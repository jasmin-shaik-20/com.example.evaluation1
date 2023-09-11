package com.example.methods

import com.example.di.appModule
import com.example.repository.UserRepository
import com.example.services.UserServices
import com.example.services.verficationServices.*
import io.mockk.coEvery
import io.mockk.mockk
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.assertTrue

class StageMethodsTest : KoinComponent{

    private val stageMethods by inject<StageMethods>()
    private val mobileVerificationService by inject<MobileVerificationService>()
    private val emailVerificationService by inject<EmailVerificationService>()
    private val aadhaarVerificationService by inject<AadhaarVerificationService>()
    private val mpinVerificationService by inject<MPINVerificationService>()
    private val twoFactorVerificationService by inject<TwoFactorVerificationService>()
    private val panVerificationService by inject<PANVerificationService>()

    @Before
    fun setup(){
        startKoin {
            modules(appModule)
        }
    }

    @After
    fun teardown(){
        stopKoin()
    }


    @Test
    fun testStage1Completed(){
        mobileVerificationService.verifyMobileNumber("9390547915")
        val isStage1Completed = stageMethods.isStageCompleted(1)
        assertTrue(isStage1Completed, "Stage 1 should be completed")
    }

    @Test
    fun testStage2Completed() {
        emailVerificationService.verifyEmail("jasmin123@gmail.com")
        val isCompleted = stageMethods.isStageCompleted(2)
        assertTrue(isCompleted, "Stage 2 should be completed")
    }

    @Test
    fun testStage3Completed(){
        mpinVerificationService.verifyMPIN("456723")
        val isCompleted=stageMethods.isStageCompleted(3)
        assertTrue(isCompleted,"Stage 3 should completed")
    }

    @Test
    fun testStage4Completed(){
        aadhaarVerificationService.verifyAadhaarNumber("654389456321")
        val isCompleted=stageMethods.isStageCompleted(4)
        assertTrue(isCompleted,"Stage 4 should completed")
    }

    @Test
    fun testStage5Completed(){
        panVerificationService.verifyPANCard("ABCDE1234F")
        val isCompleted=stageMethods.isStageCompleted(5)
        assertTrue(isCompleted,"Stage 5 should completed")
    }

    @Test
    fun testStage6Completed(){
        twoFactorVerificationService.verify2FA("452134")
        val isCompleted=stageMethods.isStageCompleted(6)
        assertTrue(isCompleted,"Stage 6 should completed")
    }


}