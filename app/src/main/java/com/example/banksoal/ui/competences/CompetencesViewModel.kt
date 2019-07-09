package com.example.banksoal.ui.competences

import android.databinding.Observable
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.example.banksoal.data.DataManager
import com.example.banksoal.data.model.CourseData
import com.example.banksoal.ui.base.BaseViewModel
import com.example.banksoal.utils.rx.SchedulerProvider

class CompetencesViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<CompetencesNavigator>(dataManager, schedulerProvider) {

    private var mCompetences: ObservableList<String> = ObservableArrayList<String>()
    private var mCompetencesStringify: ObservableField<String> = ObservableField()

    fun onCourseClick() {
        getNavigator().startQuiz()
    }

    fun onNavBackClick() {
        getNavigator().goBack()
    }

    fun getCompetences(): ObservableList<String> {
        return mCompetences
    }

    fun getCompetencesStringify(): ObservableField<String> {
        return mCompetencesStringify
    }

    fun loadCompetences(courseId: Long) {
        mCompetences.clear()
        compositeDisposable
            .add(dataManager
                .getCourse(courseId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe { it ->
                    mCompetences.addAll(it.getCompetences())
                    val builder = StringBuilder()
                    it.getCompetences().forEach { s: String? ->
                        builder.append("$s\n")
                    }
                    mCompetencesStringify.set(builder.toString())
                })
    }
}