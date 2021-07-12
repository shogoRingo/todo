package com.ringo.httpapi.util

fun String.emailValidate(): Boolean {

    val  emailPattern = "[a-zA-Z0-9._-]+@[a-z-A-Z0-9._-]+\\.+[a-z]+"
    val regex = Regex(pattern = emailPattern)
    
    return regex.matches(this)
}