package com.example.banksoal.ui.main

import com.example.banksoal.data.DataManager
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider
import android.text.TextUtils
import android.databinding.ObservableField

class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator>(dataManager, schedulerProvider) {

    val appVersion = ObservableField<String>()

    var fullName = ObservableField<String>()

    var userName = ObservableField<String>()

    val profilePicUrl = ObservableField<String>()

    fun loadUserData(){
        userName.set(dataManager.preferencesHelper.currentUserName)
        fullName.set(dataManager.preferencesHelper.currentFullName)
    }

    fun logout() {
        isLoading.set(true)
        compositeDisposable.add(
            dataManager.doLogout()
            .doOnSuccess { dataManager.setUserAsLoggedOut() }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                isLoading.set(false)
                getNavigator().openLoginActivity()
            }, { throwable ->
                isLoading.set(false)
                getNavigator().handleError(throwable)
            })
        )
    }

    fun onNavMenuCreated() {
        val currentUserName = dataManager.preferencesHelper.currentUserName
        if (!TextUtils.isEmpty(currentUserName)) {
            userName.set(currentUserName)
        }
    }
}