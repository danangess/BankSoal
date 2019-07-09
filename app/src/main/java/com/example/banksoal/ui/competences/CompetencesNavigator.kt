package com.example.banksoal.ui.competences

interface CompetencesNavigator {
    fun handleError(throwable: Throwable)
    fun openCourseActivity()
    fun startQuiz()
    fun goBack()
}