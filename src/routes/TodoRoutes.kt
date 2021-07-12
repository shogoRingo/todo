package com.ringo.httpapi.routes

// routing, CRUD
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.delete
import io.ktor.routing.put

// common
import io.ktor.application.call
import io.ktor.application.Application
import io.ktor.routing.routing

//response
import io.ktor.response.respond
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive

// koin
import org.koin.ktor.ext.inject

import com.ringo.httpapi.service.*
import com.ringo.httpapi.model.Todo
import com.ringo.httpapi.model.DeleteID
import com.ringo.httpapi.model.UserSession

// session
import io.ktor.sessions.sessions
import io.ktor.sessions.get

fun Route.todoRouting() {
    
    val todoService by inject<TodoService>()

    route("/todo") {
        get {
            call.respond(
                HttpStatusCode.OK,
                todoService.getAllTodos()
            )
        }
        post {
            val todo = call.receive<Todo>()
            val user = call.sessions.get<UserSession>()
            if(user != null) {
                todoService.createTodo(todo, user.user.userName, user.user.id)
                call.respond(HttpStatusCode.OK)
            }else {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        delete {
            val ids = call.receive<DeleteID>()
            todoService.deleteTodos(ids.idList)
            call.respond(HttpStatusCode.OK)
        }
        put {
            val todo = call.receive<Todo>()
            todoService.updateTodo(todo)
            call.respond(HttpStatusCode.OK)
        }
    }

}