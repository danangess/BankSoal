package com.example.banksoal.ui.main

interface MainNavigator {
    fun handleError(throwable: Throwable)

    fun openLoginActivity()

    fun showAboutFragment()

    fun showCourseFragment()

    fun showMainFragment()
}