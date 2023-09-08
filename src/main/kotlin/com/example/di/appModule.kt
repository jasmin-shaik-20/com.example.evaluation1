package com.example.di

import com.example.dao.StageDao
import com.example.dao.UserDao
import com.example.methods.UserMethods
import com.example.repository.UserRepository
import com.example.repository.StageRepository
import com.example.services.*
import com.example.services.verficationServices.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val appModule= module {
    singleOf(::UserRepository) {bind<UserDao>()}
    single { UserServices() }
    single { StageServices() }
    single { UserMethods() }
    single { MobileVerificationService() }
    single { EmailVerificationService() }
    single { MPINVerificationService() }
    single { AadhaarVerificationService() }
    single { PANVerificationService() }
    single { TwoFactorAuthenticationService() }

}