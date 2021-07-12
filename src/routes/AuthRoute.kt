package com.ringo.httpapi.routes

import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import java.util.UUID

// common
import io.ktor.application.call
import io.ktor.application.Application
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpMethod
import io.ktor.routing.routing
import io.ktor.routing.method
import io.ktor.auth.authentication
import io.ktor.auth.form

// model
import com.ringo.httpapi.model.LoginForm

//inject
import org.koin.ktor.ext.inject
import com.ringo.httpapi.service.AuthService

import com.ringo.httpapi.model.User
import com.ringo.httpapi.model.UserSession

// session
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.sessions.clear

fun Route.authRouting() {

    val authService by inject<AuthService>()

    route("/login") {

        get {
            // ログイン情報(email, password)を受け取り、セッションに
            val loginForm = call.receive<LoginForm>()
            val user = authService.getUser(loginForm.email, loginForm.password)
            
            // セッション情報の設定
            if (user != null) {
                call.sessions.set(UserSession(user))
                call.respond(HttpStatusCode.OK)
            }else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        post {
            val loginForm = call.receive<LoginForm>()
            // 登録済みかどうかチェック
            if(!authService.isRegistered(loginForm.email)) {
                authService.createUser(loginForm)
                call.respond(HttpStatusCode.OK)
            }else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
    route("/logout") {
        get {
            call.sessions.clear<UserSession>()
            call.respond(HttpStatusCode.OK)
        }
    }
}
