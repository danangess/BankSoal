package com.example.banksoal.ui.splash

import com.example.banksoal.data.DataManager
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider

class SplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SplashNavigator>(dataManager, schedulerProvider) {

    fun startSeeding() {
        compositeDisposable.add(
            dataManager
                .truncateDatabaseOptions()
                .flatMap<Any> { dataManager.truncateDatabaseQuestions() }
                .flatMap<Any> { dataManager.truncateDatabaseCourses() }
                .flatMap<Any> { dataManager.seedDatabaseCourses() }
//                .seedDatabaseCourses()
                .flatMap<Any> { dataManager.seedDatabaseQuestions() }
                .flatMap<Any> { dataManager.seedDatabaseOptions() }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ }, { t -> getNavigator().handleError(t) })
        )
    }

    fun decideNextActivity() {
        getNavigator().openMainActivity()
//        if (dataManager.preferencesHelper.loginMode == DataManager.LoggedInMode.LOGOUT) {
//            getNavigator().openLoginActivity()
//        } else {
//            getNavigator().openMainActivity()
//        }
    }
}