package com.ringo.httpapi.model

import io.ktor.auth.Principal
import java.util.UUID

data class UserSession(val user: User, val loggedIn:Boolean=false): Principal