package com.example

import com.example.methods.StageMethodsTest
import com.example.methods.UserMethodsTest
import com.example.plugins.*
import com.example.repository.UserRepositoryTest
import com.example.routes.UserRoutesTest
import com.example.services.UserServicesTest
import com.example.services.VerificationServices.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.runner.RunWith
import org.junit.runners.Suite
import kotlin.test.*


@RunWith(Suite::class)
@Suite.SuiteClasses(
    StageMethodsTest::class,
    UserMethodsTest::class,
    UserRepositoryTest::class,
    UserRoutesTest::class,
    AadharServiceTest::class,
    EmailServiceTest::class,
    MobileServiceTest::class,
    MPINServiceTest::class,
    PANServiceTest::class,
    TwoFactorServiceTest::class,
    UserServicesTest::class
)
class ApplicationTest


