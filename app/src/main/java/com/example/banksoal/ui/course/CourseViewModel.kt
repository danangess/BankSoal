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

    fun onCourseClick() {
        getNavigator().openQuizActivity()
    }

    fun getCourseDataList(): ObservableList<CourseData> {
        return mCourseDataList
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
}