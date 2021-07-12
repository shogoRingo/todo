package com.ringo.httpapi.module

import org.koin.dsl.module
import com.ringo.httpapi.service.TodoServiceImpl
import com.ringo.httpapi.service.TodoService

val todoModule = module(createdAtStart = true) {
    single<TodoService> { TodoServiceImpl() }
}