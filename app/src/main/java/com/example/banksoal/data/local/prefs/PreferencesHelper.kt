package com.example.banksoal.data.local.prefs

import com.example.banksoal.data.DataManager

interface PreferencesHelper {
    var currentFullName: String?

    var currentUserName: String?

    var loginMode: DataManager.LoggedInMode
}