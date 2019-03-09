package com.example.banksoal.ui.splash

import com.example.banksoal.data.DataManager
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider

class SplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SplashNavigator>(dataManager, schedulerProvider) {

    fun startSeeding() {
        compositeDisposable.add(
            dataManager
                .seedDatabaseCourses()
                .flatMap<Any> { dataManager.seedDatabaseQuestions() }
                .flatMap<Any> { dataManager.seedDatabaseOptions() }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({  }, { t -> getNavigator().handleError(t) })
        )
    }

    fun decideNextActivity() {
        if (dataManager.preferencesHelper.loginMode == DataManager.LoggedInMode.LOGOUT) {
            getNavigator().openLoginActivity()
        } else {
            getNavigator().openMainActivity()
        }
    }
}