package com.example.banksoal.data.local.db

import com.example.banksoal.data.model.db.Question
import io.reactivex.Observable

interface QuestionRepository: BaseRepository<Question, Long> {
    fun getAllByCourseId(courseId: Long): Observable<List<Question>>
}