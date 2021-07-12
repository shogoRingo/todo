package com.ringo.httpapi.util

import java.security.MessageDigest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun hashStr(password: String) : String = BCryptPasswordEncoder().encode(password)

fun isRightPass(hashedPass: String, password: String): Boolean = BCryptPasswordEncoder().matches(password, hashedPass)

// fun hashStr(password: String): String = 
//     getDigestFunction("SHA-256") { "ktor${it.length}" }(password)  
//     .joinToString(separator=""){"02x".format(it)}



// fun hashStr(password: String): String = 
// MessageDigest.getInstance("SHA-256")
// .digest(password.toByteArray())
// .joinToString(separator=""){"02x".format(it)}

// kdfjpamjvefpjmaodjmxpqeomtopevqqpmj


//Bycrypt