package com.example.routes

import com.example.database.table.Stages
import com.example.database.table.Users
import com.example.model.InputData
import com.example.repository.UserRepository
import com.example.utils.appConstants.ApiEndPoints
import io.ktor.http.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.component.KoinComponent
import com.example.utils.H2Database
import io.ktor.client.request.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.After
import org.koin.core.context.stopKoin
import java.sql.Connection


class UserRoutesTest {

    private val userRepository = UserRepository()
    private lateinit var database:Database

    @Before
    fun setup(){
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
    fun testGetUserRoute()= testApplication {
        val user=InputData("jasmin","jasmin123@gmail.com")
        val userId=userRepository.createUser(user)
        val response=client.get("${ApiEndPoints.USER}/$userId"){
            headers[HttpHeaders.ContentType]= ContentType.Application.Json.toString()
        }
        assertEquals(HttpStatusCode.OK,response.status)
    }

    @Test
    fun testPostUserRoute()= testApplication {
        val nameMinLength=System.getenv("nameMinLength").toIntOrNull()
        val nameMaxLength=System.getenv("nameMaxLength").toIntOrNull()
        val emailMinLength=System.getenv("emailMinLength").toIntOrNull()
        val emailMaxLength=System.getenv("emailMaxLength").toIntOrNull()
        val user=InputData("jasmin","jasmin123@gmail.com")
        if(user.name.length in nameMinLength!!..nameMaxLength!!
            && user.email.length in emailMinLength!!..emailMaxLength!!){
            val serializedUser=Json.encodeToString(user)
            val response=client.post(ApiEndPoints.USER){
                headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
                setBody(serializedUser)
            }
            assertEquals(HttpStatusCode.OK,response.status)
        }
    }

    @Test
    fun testPostUpdateStage()= testApplication {
        val user=InputData("jasmin","jasmin123@gmail.com")
        val userId=userRepository.createUser(user)
        val response=client.post("${ApiEndPoints.USER}/$userId/updateStage"){
            headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
        }
        assertEquals(HttpStatusCode.OK,response.status)
    }


}



