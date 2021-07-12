package com.ringo.httpapi.module

import org.koin.dsl.module
import com.ringo.httpapi.service.AuthService
import com.ringo.httpapi.service.AuthServiceImpl

val authModule = module(createdAtStart = true){
    single<AuthService> { AuthServiceImpl() }
}