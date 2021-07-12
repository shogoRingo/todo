package com.ringo.httpapi.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.jodatime.datetime
import java.util.UUID

data class Todo(
    /** id */
    val id: UUID,
    /** userId */
    val userId: UUID,
    /** username */
    val userName: String,
    /** todo content */
    val content: String,
    /** expire date */
    val expire: String,
    /** importance 3 > 2 > 1 \*/
    val importance: Int,
    /** status 1:done, 2:undone \*/
    val status: Int,
    )

object Todos: Table() {
    val id = uuid("id").autoGenerate()
    val userId = uuid("userId").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val userName = varchar("userName", 255)
    val content = varchar("content", 255)
    val expire = datetime("expire")
    val importance = integer("importance")
    val status = integer("status")
    override val primaryKey = PrimaryKey(id)
}
