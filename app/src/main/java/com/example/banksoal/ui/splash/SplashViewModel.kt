package com.example.banksoal.ui.splash

import com.example.banksoal.data.DataManager
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider

class SplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SplashNavigator>(dataManager, schedulerProvider) {
    fun startSeeding() {
//        getCompositeDisposable().add(getDataManager()
//            .seedDatabaseQuestions()
//            .flatMap<Any> { getDataManager().seedDatabaseOptions() }
//            .subscribeOn(getSchedulerProvider().io())
//            .observeOn(getSchedulerProvider().ui())
//            .subscribe({ decideNextActivity() }, { decideNextActivity() })
//        )
    }

    fun decideNextActivity() {
        if (dataManager.getLoginMode() == DataManager.LoggedInMode.LOGOUT) {
            getNavigator().openLoginActivity()
        } else {
            getNavigator().openMainActivity()
        }
    }
}