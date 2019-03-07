package com.example.banksoal.data.local.prefs

import com.example.banksoal.data.DataManager

interface PreferencesHelper {
    fun getCurrentUserName(): String?

    fun setCurrentUserName(userName: String?)

    fun setLoginMode(loggedInMode: DataManager.LoggedInMode)

    fun getLoginMode(): DataManager.LoggedInMode
}