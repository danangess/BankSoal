package com.example.banksoal.ui.user_guide

import android.databinding.ObservableField
import com.example.banksoal.data.DataManager
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider

class UserGuideViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<UserGuideNavigator>(dataManager, schedulerProvider) {

    init {
        setText()
    }

    private val mText: ObservableField<String> = ObservableField()

    fun getText(): ObservableField<String> {
        return mText
    }

    private fun setText() {
        compositeDisposable
            .add(dataManager
                .getUserGuide()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe {
                    mText.set(it.joinToString("\n"))
                })
    }
}