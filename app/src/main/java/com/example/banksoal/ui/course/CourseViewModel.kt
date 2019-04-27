package com.example.banksoal.ui.course

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.example.banksoal.data.DataManager
import com.example.banksoal.data.model.CourseData
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider

class CourseViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<CourseNavigator>(dataManager, schedulerProvider) {

    private var mCourseDataList: ObservableList<CourseData> = ObservableArrayList<CourseData>()
    private var mQuestionGroupDataList: ObservableList<String> = ObservableArrayList<String>()

    fun onCourseClick() {
        getNavigator().openQuizActivity()
    }

    fun onCourseChanged() {
        getNavigator().onCourseSelected()
    }

    fun onGroupChanged() {
        getNavigator().onQuestionGroupSelected()
    }

    fun getCourseDataList(): ObservableList<CourseData> {
        return mCourseDataList
    }

    fun getQuestionGroupDataList(): ObservableList<String> {
        return mQuestionGroupDataList
    }

    fun loadCourseDataList() {
        mCourseDataList.clear()
        compositeDisposable
            .add(dataManager
                .getCourseData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { t: List<CourseData>? ->
                    if (t != null){
                        mCourseDataList.addAll(t)
                    }
                })
    }

    fun loadQuestionGroupDataList(courseId: Long) {
        mQuestionGroupDataList.clear()
        compositeDisposable
            .add(dataManager
                .getQuestionGroupData(courseId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe {t: List<String>? ->
                    if (t != null) {
                        mQuestionGroupDataList.addAll(t)
                    }
                })
    }
}