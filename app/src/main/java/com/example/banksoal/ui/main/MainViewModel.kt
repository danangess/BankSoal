package com.example.banksoal.ui.main

import com.example.banksoal.data.DataManager
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider
import android.text.TextUtils
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import com.example.banksoal.data.model.QuestionData

class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator>(dataManager, schedulerProvider) {

    init {
        // TODO: get courseId
        loadQuestionCards(1)
    }

    companion object {
        val NO_ACTION = -1
        val ACTION_ADD_ALL = 0
        val ACTION_DELETE_SINGLE = 1
    }

    val appVersion = ObservableField<String>()

    private var questionCardData: MutableLiveData<List<QuestionData>> = MutableLiveData<List<QuestionData>>()

    private val questionDataList = ObservableArrayList<QuestionData>()

    val fullName = ObservableField<String>()

    val userName = ObservableField<String>()

    val userProfilePicUrl = ObservableField<String>()

    var action: Int = NO_ACTION

    fun getQuestionCardData(): MutableLiveData<List<QuestionData>> {
        return questionCardData
    }

    fun getQuestionDataList(): ObservableList<QuestionData> {
        return questionDataList
    }

    fun setQuestionDataList(questionCardDatas: List<QuestionData>) {
        action = ACTION_ADD_ALL
        questionDataList.clear()
        questionDataList.addAll(questionCardDatas)
    }

    fun loadQuestionCards(courseId: Long) {
        compositeDisposable.add(dataManager
            .getQuestionData(courseId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ questionList ->
                if (questionList != null) {
                    action = ACTION_ADD_ALL
                    questionCardData.value = questionList
                }
            }, {

            })
        )
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
        val currentUserName = dataManager.getCurrentUserName()
        if (!TextUtils.isEmpty(currentUserName)) {
            userName.set(currentUserName)
        }
    }

    fun removeQuestionCard() {
        action = ACTION_DELETE_SINGLE
        questionDataList.removeAt(0)
//        questionCardData.value.removeAt(0)
    }

    fun updateAppVersion(version: String) {
        appVersion.set(version)
    }
}