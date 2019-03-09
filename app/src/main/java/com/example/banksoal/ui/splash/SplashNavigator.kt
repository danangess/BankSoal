package com.example.banksoal.ui.splash

interface SplashNavigator {
    fun handleError(throwable: Throwable)

    fun openLoginActivity()

    fun openMainActivity()
}