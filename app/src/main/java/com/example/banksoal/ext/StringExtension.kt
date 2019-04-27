package com.example.banksoal.ext

fun String.isUsername(): Boolean {
    val regex = Regex("^[a-z0-9_-]{3,15}$")
    return regex.matches(this)
}