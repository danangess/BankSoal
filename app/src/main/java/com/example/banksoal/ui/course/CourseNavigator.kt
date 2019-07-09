package com.example.banksoal.ui.course

interface CourseNavigator {
    fun handleError(throwable: Throwable)
    fun openQuizActivity()
    fun openCompetencesFragment()
    fun onCourseSelected()
    fun onQuestionGroupSelected()
}