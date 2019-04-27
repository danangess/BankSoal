package com.example.banksoal.ui.course

interface CourseNavigator {
    fun handleError(throwable: Throwable)
    fun openQuizActivity()
    fun onCourseSelected()
    fun onQuestionGroupSelected()
}