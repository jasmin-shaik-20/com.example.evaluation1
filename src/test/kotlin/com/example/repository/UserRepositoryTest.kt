package com.example.repository

import com.example.database.table.Stages
import com.example.database.table.Stages.userId
import com.example.database.table.Users
import com.example.database.table.Users.currentStage
import com.example.database.table.Users.nextStage
import com.example.methods.UserMethods
import com.example.model.InputData
import com.example.utils.H2Database
import com.example.utils.appConstants.GlobalConstants
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.testng.Assert.assertNotNull
import java.sql.Connection
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserRepositoryTest {

    private val userRepository = UserRepository()

    private lateinit var database: Database

    @Before
    fun setup() {
        database = H2Database.init()
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ

        transaction(database) {
            SchemaUtils.create(Users, Stages)
        }
    }

    @After
    fun tearDown() {
        transaction(database) {
            SchemaUtils.drop(Users, Stages)
        }
    }

    @Test
    fun testCreateUser() = runBlocking {
        val user = InputData("jasmin", "jasmin123@gmail.com")
        val newUser = userRepository.createUser(user)
        val uuid = newUser.toString()
        assertEquals(newUser.toString(), uuid)
    }

    @Test
    fun testGetUser() = runBlocking {
        val user = InputData("jasmin", "jasmin123@gmail.com")
        val newUser = userRepository.createUser(user)
        val getUser = userRepository.getUserById(newUser)
        assertEquals("jasmin", getUser?.name)
        assertEquals("jasmin123@gmail.com", getUser?.email)
    }

    @Test
    fun testUpdateStage() = runBlocking {
        val user = InputData("jasmin", "jasmin123@gmail.com")
        val newUser = userRepository.createUser(user)
        val nextStage = 2
        userRepository.updateStage(newUser.toString(), nextStage)
        val updatedUser = userRepository.getUserById(newUser)
        assertNotNull(updatedUser, "Updated user should not be null")
        assertEquals(nextStage, updatedUser!!.currentStage, "Current stage should match next stage")
        assertEquals(nextStage + 1, updatedUser.nextStage, "Next stage should be incremented by 1")
    }

    @Test
    fun testUpdateIsVerified() = runBlocking {
        val user = InputData("jasmin", "jasmin123@gmail.com")
        val newUser = userRepository.createUser(user)
        val isVerified = true
        userRepository.updateIsVerified(newUser.toString(), isVerified)
        val updatedUser = userRepository.getUserById(newUser)
        assertNotNull(updatedUser, "Updated user should not be null")
        assertEquals(isVerified, updatedUser!!.isVerified, "isVerified status should match expected value")
    }

    @Test
    fun testIsExpired_NotExpired() = runBlocking {
        val user = InputData("jasmin", "jasmin123@gmail.com")
        val newUser = userRepository.createUser(user)
        val getUser = userRepository.getUserById(newUser)
        val lastStageUpdate = getUser!!.lastStageUpdate
        val currentTime = System.currentTimeMillis()
        val isExpired = userRepository.isExpired(newUser.toString(), currentTime)
        assertFalse(isExpired, "User should not be expired")
    }

    @Test
    fun testResetStage() = runBlocking{
        val user = InputData("jasmin", "jasmin123@gmail.com")
        val newUser = userRepository.createUser(user)
        userRepository.resetStage(newUser.toString())
        val updatedUser = userRepository.getUserById(newUser)
        assertEquals(1, updatedUser?.currentStage)
        assertEquals(2, updatedUser?.nextStage)
        assertEquals(false, updatedUser?.isVerified)
        assertTrue(updatedUser?.lastStageUpdate!! >= System.currentTimeMillis() - 1000) // Check if lastStageUpdate is within 1 second of the current time
    }

}