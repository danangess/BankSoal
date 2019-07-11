package com.example.banksoal.ui.signup

import android.text.TextUtils
import com.example.banksoal.data.DataManager
import com.example.banksoal.data.model.db.User
import com.example.banksoal.ext.isUsername
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider

class SignupViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SignupNavigator>(dataManager, schedulerProvider) {

    fun registerUser(username: String, password: String, firstName: String, lastName: String) {
        isLoading.set(true)
        var isError = false
        if (!username.isUsername()) {
            getNavigator().handleError(Throwable("Wrong username"))
            isError = true
        }
        if (TextUtils.isEmpty(password)) {
            getNavigator().handleError(Throwable("Wrong password"))
            isError = true
        }
        if (isError) {
            return
        }
        val user = User()
        user.username = username
        user.password = password
        user.firstName = firstName
        user.lastName = lastName

        compositeDisposable.add(dataManager
            .doSignUp(firstName, lastName, username, password)
            .doOnSuccess { success ->
                if (success) {
                    dataManager.doLogin(username, password)
                }
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ success ->
                isLoading.set(false)
                if (success)
                    getNavigator().openMainActivity()
                else
                    getNavigator().handleError(Throwable("Failed"))
            }, { t ->
                isLoading.set(false)
                getNavigator().handleError(t)
            })
        )
    }

    fun onSignUpClick() {
        getNavigator().signUp()
    }

    fun onNavBackClick() {
        getNavigator().goBack()
    }
}