package com.ringo.httpapi.service

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.*

import com.ringo.httpapi.model.User
import com.ringo.httpapi.model.Users
import com.ringo.httpapi.model.LoginForm
import com.ringo.httpapi.util.hashStr
import com.ringo.httpapi.util.isRightPass
import com.ringo.httpapi.util.emailValidate

interface AuthService {
    fun getUser(email: String, password: String): User?
    fun createUser(loginForm: LoginForm): Boolean
    fun updateUser(user: User): Unit
    fun isRegistered(email: String): Boolean
}

class AuthServiceImpl: AuthService {

        override fun getUser(email: String, password: String): User? {
        
            var user: List<User> = transaction {
                Users.select {Users.email eq email}.map {
                    User(
                        id = it[Users.id],
                        userName = it[Users.userName],
                        password = it[Users.password],
                        email = it[Users.email],
                        isStaff = it[Users.isStaff],
                        isActive = it[Users.isActive],
                    )
                }
            }
            if(user.isEmpty()) return null
        
            if (!isRightPass(user[0].password, password)) return null

            return user[0]

    }

    override fun createUser(loginForm: LoginForm): Boolean {
        if(loginForm.email.emailValidate()) {
            transaction {
                Users.insert{
                    it[userName] = loginForm.userName
                    it[password] = hashStr(loginForm.password)
                    it[email] = loginForm.email
                }
            }
            return true
        }else {
            return false
        }
    }

    override fun updateUser(user: User): Unit = transaction {
        Users.update({Users.id eq user.id}) {
            it[userName] = user.userName
            it[password] = hashStr(user.password)
            it[email] = user.email
        }
    }

    override fun isRegistered(email: String): Boolean {
        var user: List<User>? = transaction{
            Users.select{ Users.email eq email }.map{
                User(
                    id = it[Users.id],
                    userName = it[Users.userName],
                    password = it[Users.password],
                    email = it[Users.email],
                    isStaff = it[Users.isStaff],
                    isActive = it[Users.isActive],
                )
            }
        }
        return user?.isNotEmpty() == true
    }
    

}