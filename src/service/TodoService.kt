package com.ringo.httpapi.service

//custome model
import com.ringo.httpapi.model.Todo
import com.ringo.httpapi.model.Todos

//transaction, exposed CRUD
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*

//Datetime
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

//UUID
import java.util.UUID

interface TodoService {
    fun getAllTodos(): List<Todo>
    fun createTodo(todo: Todo, userName: String, userId: UUID): Unit 
    fun deleteTodos(idList: List<UUID>): Unit 
    fun updateTodo(todo: Todo): Unit 
}

class TodoServiceImpl: TodoService {
    companion object {
        val DEF_FMT: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    }

    override fun getAllTodos(): List<Todo> = transaction {
        Todos.selectAll().map{
            print(it)
            Todo(
                id = it[Todos.id],
                userName = it[Todos.userName],
                userId = it[Todos.userId],
                content = it[Todos.content],
                expire = DEF_FMT.print(it[Todos.expire]),
                importance = it[Todos.importance],
                status = it[Todos.status]
        )}
    }

    override fun createTodo(todo: Todo, userName: String, userId: UUID): Unit = transaction {
        Todos.insert{
            it[Todos.userName] = userName
            it[Todos.userId] = userId
            it[Todos.content] = todo.content
            it[Todos.expire] = DateTime.parse(todo.expire)
            it[Todos.importance] = todo.importance
            it[Todos.status] = todo.status
        }
    }

    override fun deleteTodos(idList: List<UUID>): Unit = transaction {
        Todos.deleteWhere{ Todos.id inList idList }
    }

    override fun updateTodo(todo: Todo): Unit = transaction {
        Todos.update({Todos.id eq todo.id}) {
            it[userName] = todo.userName
            it[userId] = todo.userId
            it[content] = todo.content
            it[expire] = DateTime.parse(todo.expire)
            it[importance] = todo.importance
            it[status] = todo.status
         }
    }

}