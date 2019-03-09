package com.example.banksoal.ui.login

import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.data.DataManager
import android.text.TextUtils
import com.example.banksoal.utils.CommonUtils
import com.example.banksoal.utils.rx.SchedulerProvider

class LoginViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<LoginNavigator>(dataManager, schedulerProvider) {

    fun isUsernameAndPasswordValid(username: String, password: String): Boolean {
        // validate email and password
        if (TextUtils.isEmpty(username)) {
            return false
        }
        if (!CommonUtils.isUsernameValid(username)) {
            return false
        }
        return !TextUtils.isEmpty(password)
    }

    fun doLogin(username: String, password: String) {
        isLoading.set(true)
        compositeDisposable.add(dataManager
            .doLogin(username, password)
            .doOnSuccess { success ->
                if (success)
                {
                    dataManager.updateUserInfo(username)
                    dataManager.preferencesHelper.loginMode = DataManager.LoggedInMode.LOGIN
                }
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ success ->
                isLoading.set(false)
                if (success)
                    getNavigator().openMainActivity()
                else
                    getNavigator().handleError(Throwable("User is not registered"))
            }, { throwable ->
                isLoading.set(false)
                getNavigator().handleError(throwable)
            })
        )
    }

    fun onLoginClick() {
        getNavigator().login()
    }

    fun onSignUpClick() {
        getNavigator().openSignUpFragment()
    }
}