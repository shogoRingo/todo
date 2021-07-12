package com.ringo.httpapi

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.util.hex

import com.ringo.httpapi.routes.*
import com.ringo.httpapi.module.*
import com.ringo.httpapi.model.UserSession

import com.ringo.httpapi.service.DatabaseFactory
import com.ringo.httpapi.service.DatabaseFactoryImpl
import org.jetbrains.exposed.sql.Database

import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.sessions.clear
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication

import io.ktor.auth.Authentication
import io.ktor.auth.session
import io.ktor.auth.UnauthorizedResponse
import io.ktor.auth.authenticate
import io.ktor.auth.principal


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
        }
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(Koin) { 
        slf4jLogger()
        modules(todoModule)
        modules(authModule)
    }

    install(Sessions) {
        val secretHashKey = hex("414950D2CCB85C320DFC1C77F3C5FDE1B1EF39CE0DD97885F86D4EA7855D454294B5A845ECC7B9C1C5FCB88043D46A1582AF")

        cookie<UserSession>("user_session", SessionStorageMemory()){
            transform(SessionTransportTransformerMessageAuthentication(secretHashKey, "HmacSHA256"))
        }
    }
    install(Authentication) {
        session<UserSession>("auth-session") {
            validate {session -> 
                if(session.user.toString().isNotEmpty()) {
                    session
                }else{
                    null
                }
            
            }
            challenge { call.respond(UnauthorizedResponse()) }
        }
    }

    Database.connect(DatabaseFactoryImpl.create())
    DatabaseFactoryImpl.initDB()

    routing {
        authRouting()

        authenticate("auth-session") {
            todoRouting()
        }
    }
}