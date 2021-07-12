package com.ringo.httpapi.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime
import java.util.UUID

data class User(
    val id: UUID,
    val userName: String,
    val password: String,
    val email: String,
    val isStaff: Boolean,
    val isActive: Boolean,
)

object Users: Table() {
    val id = uuid("id").autoGenerate()
    val userName = varchar("userName", 255)
    val password = varchar("password", 1000)
    val email = varchar("email", 255)
    val isStaff = bool("isStaff").default(false)
    val isActive = bool("isActive").default(true)
    val createdAt = datetime("createdAt").default(DateTime.now())
    override val primaryKey = PrimaryKey(id)
}