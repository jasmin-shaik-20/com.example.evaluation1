package com.example.services

import com.example.database.table.Stages
import com.example.database.table.Users
import com.example.di.appModule
import com.example.exceptions.InvalidLengthException
import com.example.exceptions.UserAlreadyExistException
import com.example.exceptions.UserNotFoundException
import com.example.model.InputData
import com.example.repository.UserRepository
import com.example.utils.H2Database
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
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.testng.Assert.assertNotNull
import java.sql.Connection
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UserServicesTest : KoinComponent {

    private val userServices by inject<UserServices>()
    private val userRepository by inject<UserRepository>()
    private lateinit var database: Database

    @Before
    fun setup() {
        startKoin {
            modules(appModule)
        }
        database= H2Database.init()
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_REPEATABLE_READ

        transaction(database) {
            SchemaUtils.create(Users,Stages)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        transaction(database) {
            SchemaUtils.drop(Users,Stages)
        }
    }

    @Test
    fun testHandleGetUser()= runBlocking {
        val user=InputData("jasmin","jasmin123@gmail.com")
        val createUser=userRepository.createUser(user)
        val getUser=userServices.handleGetUser(createUser)
        assertEquals(getUser.name,user.name)
        assertEquals(getUser.email,user.email)
    }

    @Test
    fun testHandlePostUser() = runBlocking {
        val user = InputData("jasminshaik", "jasmin1234@gmail.com")
        try {
            val userId = userServices.handlePostUser(user, 4, 50, 5, 50)
            val existingUser = userRepository.getUserByNameOrEmail(user.name, user.email)
            val createdUser = userRepository.getUserById(userId)
            assertEquals(user.name, createdUser?.name)
            assertEquals(user.email, createdUser?.email)
        } catch (e: UserAlreadyExistException) {
            val existingUser = userRepository.getUserByNameOrEmail(user.name, user.email)
            assertNotNull(existingUser)
        }
    }

    //failure
    @Test
    fun testHandlePostUserInvalidLength(){
        runBlocking {
            val user = InputData("j", "jasmin")
            assertFailsWith<InvalidLengthException> {
                userServices.handlePostUser(user, 4, 50, 5, 50)
            }
        }
    }

    @Test
    fun testHandleGetUserByIdNotFound(){
        runBlocking {
            val userId="47db4560-ea9e-4a65-946a-b2faae518c86"
            val uuid=UUID.fromString(userId)
            assertFailsWith<UserNotFoundException> {
                userServices.handleGetUser(uuid)
            }

        }
    }
}