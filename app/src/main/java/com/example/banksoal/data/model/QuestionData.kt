package com.example.banksoal.data.model

import com.example.banksoal.data.model.db.Option
import com.example.banksoal.data.model.db.Question

class QuestionData(val question: Question, val options: List<Option>) {
    val showCorrectOption: Boolean = true
}