package com.example.banksoal.ui.quiz

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.example.banksoal.data.DataManager
import com.example.banksoal.data.model.QuestionData
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider
import java.util.*

class QuizViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<QuizNavigator>(dataManager, schedulerProvider) {

    companion object {
        var answer: MutableMap<Long, Boolean> = mutableMapOf()
//        var correctCount: Int = 0
//        var inCorrectCount: Int = 0
    }

    private val questionCardData: MutableLiveData<List<QuestionData>> = MutableLiveData()
    private val questionDataList = ObservableArrayList<QuestionData>()
    private val mIsComplete = ObservableField<Boolean>(false)

    var result = ObservableField<String>("Benar = 0\nSalah = 0")

    fun loadQuizData(courseId: Long) {
        answer = mutableMapOf()
        compositeDisposable.add(
            dataManager
                .getQuestionData(courseId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ questionList ->
                    if (questionList != null) {
                        questionCardData.value = questionList
                        getNavigator().loadQuizData()
                    }
                }, { t ->
                    getNavigator().handleError(t)
                })
        )
    }

    fun answer() {
        getNavigator().answer()
    }

    fun finish() {
        getNavigator().onFinish()
    }

    fun getQuestionCardData(): LiveData<List<QuestionData>> {
        return questionCardData
    }

    fun getQuestionDataList(): ObservableList<QuestionData> {
        return questionDataList
    }

    fun getIsComplete(): ObservableField<Boolean> {
        return mIsComplete
    }

    fun setQuestionDataList(questionCardDatas: List<QuestionData>) {
//        action = ACTION_ADD_ALL;
        questionDataList.clear()
        questionDataList.addAll(questionCardDatas)
    }

    fun updateResult() {
        if (questionCardData.value != null) {
            var correctCount = answer.filter { entry: Map.Entry<Long, Boolean> -> entry.value }.size
            var inCorrectCount = answer.filter { entry: Map.Entry<Long, Boolean> -> !entry.value }.size
            this.result.set(
                "Benar = $correctCount\n" +
                        "Salah = $inCorrectCount"
            )

            val totalQuiz = questionCardData.value!!.size
            if (totalQuiz == (correctCount + inCorrectCount)) {
                mIsComplete.set(true)
            } else {
                mIsComplete.set(false)
            }
        }
    }
}