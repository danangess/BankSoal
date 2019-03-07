package com.example.banksoal.ui.login

interface LoginNavigator {
    fun handleError(throwable: Throwable)

    fun login()

    fun openMainActivity()

    fun openSignUpFragment()
}