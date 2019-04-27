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

class QuizViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<QuizNavigator>(dataManager, schedulerProvider) {

    companion object {
        var correctCount: Int = 0
        var inCorrectCount: Int = 0
    }

    private val questionCardData: MutableLiveData<List<QuestionData>> = MutableLiveData()
    private val questionDataList = ObservableArrayList<QuestionData>()

    var result = ObservableField<String>("Loading...")

    fun loadQuizData(courseId: Long, group: String){
        correctCount = 0
        inCorrectCount = 0
        compositeDisposable.add(dataManager
            .getQuestionData(courseId, group)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ questionList ->
                if (questionList != null) {
                    questionCardData.value = questionList
                    getNavigator().loadQuizData()
                }
            }, {t ->
                getNavigator().handleError(t)
            })
        )
    }

    fun getQuestionCardData(): LiveData<List<QuestionData>>  {
        return questionCardData
    }

    fun getQuestionDataList(): ObservableList<QuestionData>  {
        return questionDataList
    }

    fun setQuestionDataList(questionCardDatas: List<QuestionData>) {
//        action = ACTION_ADD_ALL;
        questionDataList.clear()
        questionDataList.addAll(questionCardDatas)
    }

    fun updateResult() {
        if (questionCardData.value != null) {
            val correct = correctCount
            val n = questionCardData.value!!.count()
            var result = 0
            if (n > 0) {
                result = correct / n
            }
            this.result.set("$correct / $n = $result")
        }
    }
}