package com.example.banksoal.ui.quiz

import com.example.banksoal.data.model.QuestionData

interface QuizNavigator {
    fun handleError(throwable: Throwable)
    fun loadQuizData(questionData: QuestionData)
    fun answer()
    fun next()
    fun onFinish()
}