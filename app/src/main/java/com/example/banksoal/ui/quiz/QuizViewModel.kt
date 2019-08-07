package com.example.banksoal.ui.quiz

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.view.View
import com.example.banksoal.data.DataManager
import com.example.banksoal.data.model.QuestionData
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider

class QuizViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<QuizNavigator>(dataManager, schedulerProvider) {

    companion object {
        var answer: MutableMap<Long, Boolean> = mutableMapOf()
    }

    private val mQuestionCardData: MutableLiveData<QuestionData> = MutableLiveData()
//    private val mIsComplete = ObservableField(false)
//    private val mIsAnswered = ObservableField(false)
    private val mExplanation = ObservableField<String>()
    private val mCourseId = ObservableField<Long>()
    private val mNumber = ObservableField<Int>()

    private val mAnswerVisible = ObservableField(View.GONE)
    private val mNextVisible = ObservableField(View.GONE)
    private val mFinishVisible = ObservableField(View.GONE)

    var result = ObservableField("Benar = 0\nSalah = 0")

    fun loadQuizData(courseId: Long, number: Int = 1) {
        mCourseId.set(courseId)
        mNumber.set(number)

//        answer = mutableMapOf()
        compositeDisposable.add(
            dataManager
                .getQuestionData(courseId, number)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ questionData ->
                    mQuestionCardData.value = questionData
                    getNavigator().loadQuizData(questionData)
                    onQuizLoaded()
                }, {
                    setComplete()
//                    getNavigator().handleError(t)
                })
        )
    }

    fun loadNextQuizData() {
        val courseId = mCourseId.get()!!
        val number = mNumber.get()!!
        loadQuizData(courseId, number + 1)
    }

    fun answer() {
//        mIsAnswered.set(true)
        mAnswerVisible.set(View.GONE)
        getNavigator().answer()
        updateResult()
    }

    fun next() {
        getNavigator().next()
    }

    fun finish() {
        getNavigator().onFinish()
    }

    fun getExplanation(): ObservableField<String> {
        return mExplanation
    }

    fun getAnswerVisibility(): ObservableField<Int> {
        return mAnswerVisible
    }

    fun getNextVisibility(): ObservableField<Int> {
        return mNextVisible
    }

    fun getFinishVisibility(): ObservableField<Int> {
        return mFinishVisible
    }

    private fun updateResult() {
        onQuizAnswered()
        val questionDataExist = mQuestionCardData.value != null
//        val isAnswered = mIsAnswered.get()!!
        if (questionDataExist) {
            val correctCount = answer.filter { entry: Map.Entry<Long, Boolean> -> entry.value }.size
            val inCorrectCount = answer.filter { entry: Map.Entry<Long, Boolean> -> !entry.value }.size
            this.result.set(
                "Benar = $correctCount\n" +
                        "Salah = $inCorrectCount"
            )
        }
    }

    private fun loadExplanation() {
        val courseId = mCourseId.get()!!
        val number = mNumber.get()!!
        compositeDisposable.add(
            dataManager.getExplanation(courseId, number)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { t: String? ->
                    mExplanation.set(t)
                }
        )
    }

    private fun setComplete() {
        mExplanation.set(null)
//        mIsAnswered.set(true)
//        mIsComplete.set(true)
        mAnswerVisible.set(View.GONE)
        mNextVisible.set(View.GONE)
        mFinishVisible.set(View.VISIBLE)
    }

    private fun onQuizLoaded() {
        mExplanation.set(null)
//        mIsAnswered.set(false)
        mAnswerVisible.set(View.VISIBLE)
        mNextVisible.set(View.GONE)
    }

    private fun onQuizAnswered() {
//        mIsAnswered.set(true)
        mAnswerVisible.set(View.GONE)
        mNextVisible.set(View.VISIBLE)
        loadExplanation()
    }
}