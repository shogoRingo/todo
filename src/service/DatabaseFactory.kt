package com.ringo.httpapi.service

//HikariConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

import javax.sql.DataSource

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils

import com.ringo.httpapi.model.Todos
import com.ringo.httpapi.model.Users

interface DatabaseFactory {
    fun create(): DataSource
    fun initDB(): Unit
}

object DatabaseFactoryImpl: DatabaseFactory {

    override fun create(): DataSource {
        val config = HikariConfig().apply { 
            driverClassName = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:mem:test"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
         }
         return HikariDataSource(config)
    }

    override fun initDB(): Unit = transaction{
        SchemaUtils.create(Users)
        SchemaUtils.create(Todos)
    }

}