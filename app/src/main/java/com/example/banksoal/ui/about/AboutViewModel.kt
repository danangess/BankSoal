package com.example.banksoal.ui.about

import com.example.banksoal.data.DataManager
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider

class AboutViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<AboutNavigator>(dataManager, schedulerProvider) {

    fun onNavBackClick() {
        getNavigator().goBack()
    }
}