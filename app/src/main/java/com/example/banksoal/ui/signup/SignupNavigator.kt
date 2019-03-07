package com.example.banksoal.ui.signup

interface SignupNavigator {
    fun handleError(throwable: Throwable)
    fun signUp()
    fun goBack()
    fun openMainActivity()
}