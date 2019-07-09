package com.example.banksoal.ui.quiz

interface QuizNavigator {
    fun handleError(throwable: Throwable)
    fun loadQuizData()
    fun answer()
    fun onFinish()
}