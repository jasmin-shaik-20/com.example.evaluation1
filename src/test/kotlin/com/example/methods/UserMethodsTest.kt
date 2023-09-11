package com.example.methods

import com.example.database.table.Stages
import com.example.database.table.Users
import com.example.di.appModule
import com.example.model.User
import com.example.repository.UserRepository
import com.example.utils.H2Database
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.sql.Connection

class UserMethodsTest: KoinComponent{

    private lateinit var database:Database

    private val stageMethods = mockk<StageMethods>()
    private val userRepository = mockk<UserRepository>()
    private val userMethods = mockk<UserMethods>()

    @Before
    fun setup(){
        startKoin {
            modules(appModule)
        }
        database= H2Database.init()
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ

        transaction(database) {
            SchemaUtils.create(Users, Stages)
        }
    }

    @After
    fun teardown(){
        stopKoin()
        transaction(database) {
            SchemaUtils.drop(Users,Stages)
        }
    }
    @Test
    fun testCompleteCurrentStageStageCompletedNotExpired()= runBlocking{
        val userId = "47db4560-ea9e-4a65-946a-b2faae518c86"
        val currentStage = 1
        coEvery { stageMethods.isStageCompleted(currentStage) } returns true
        coEvery { userRepository.isExpired(userId, any()) } returns false
        coEvery { userMethods.completeCurrentStage(any()) } returns "Stage completed successfully"
        val result = userMethods.completeCurrentStage(User(userId, "jasmin", "jasmin123@gmail.com", currentStage, 2, false, System.currentTimeMillis()))
        assertEquals("Stage completed successfully", result)
    }

    @Test
    fun testCompleteCurrentStageStageNotCompleted()= runBlocking{
        val userId = "47db4560-ea9e-4a65-946a-b2faae518c86"
        val currentStage = 1
        coEvery { stageMethods.isStageCompleted(currentStage) } returns false
        coEvery { userRepository.isExpired(userId,any()) } returns false
        coEvery { userMethods.completeCurrentStage(any()) } returns "Stage not completed"
        val result = userMethods.completeCurrentStage(User(userId, "jasmin", "jasmin123@gmail.com", currentStage, 2, false, System.currentTimeMillis()))
        assertEquals("Stage not completed", result)
    }

    @Test
    fun testCompleteCurrentStage_StageCompletedButExpired()= runBlocking {
        val userId = "47db4560-ea9e-4a65-946a-b2faae518c86"
        val currentStage = 1
        coEvery { stageMethods.isStageCompleted(currentStage) } returns true
        coEvery { userRepository.isExpired(userId, any()) } returns true
        coEvery { userMethods.completeCurrentStage(any())} returns "Stage reset to 1 due to inactivity"
        val result = userMethods.completeCurrentStage(User(userId, "jasmin", "jasmin123@gmail.com", currentStage, 2, false, System.currentTimeMillis()))
        assertEquals("Stage reset to 1 due to inactivity", result)
    }

    @Test
    fun testCompleteCurrentStage_CompletedStage6()= runBlocking{
        val userId = "47db4560-ea9e-4a65-946a-b2faae518c86"
        val currentStage = 6
        coEvery { stageMethods.isStageCompleted(currentStage) } returns true
        coEvery { userRepository.isExpired(userId, any()) } returns false
        coEvery { userMethods.completeCurrentStage(any())} returns "Verification completed"
        val result = userMethods.completeCurrentStage(User(userId, "jasmin", "jasmin123@gmail.com", currentStage, 2, false, System.currentTimeMillis()))
        assertEquals("Verification completed", result)
    }







}