package com.example.banksoal.data.local.prefs

import android.content.Context
import javax.inject.Inject
import android.content.SharedPreferences
import com.example.banksoal.data.DataManager
import com.example.banksoal.di.PreferenceInfo

class AppPreferencesHelper
@Inject
constructor(context: Context, @PreferenceInfo prefFileName: String) : PreferencesHelper {
    private var mPrefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override var currentFullName: String? = mPrefs.getString(PREF_KEY_CURRENT_FULL_NAME, null)

    override var currentUserName: String? = mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null)

    override var loginMode: DataManager.LoggedInMode
        get() {
            val isLogin = mPrefs.getBoolean(PREF_KEY_LOGGED_IN, false)
            return if (isLogin)
                DataManager.LoggedInMode.LOGIN
            else
                DataManager.LoggedInMode.LOGOUT
        }
        set(value) {
            val isLogin = value == DataManager.LoggedInMode.LOGIN
            mPrefs.edit().putBoolean(PREF_KEY_LOGGED_IN, isLogin).apply()
        }

    companion object {
        private const val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"

        private const val PREF_KEY_CURRENT_FULL_NAME = "PREF_KEY_CURRENT_FULL_NAME"

        private const val PREF_KEY_LOGGED_IN = "PREF_KEY_LOGGED_IN"
    }
}