package com.example.banksoal.data.local.db

import com.example.banksoal.data.local.db.dao.QuestionDao
import com.example.banksoal.data.model.db.Question
import io.reactivex.Observable

interface QuestionRepository : BaseRepository<QuestionDao, Question, Long> {
    fun getAllByCourseId(courseId: Long): Observable<List<Question>>
    fun getAllByCourseId(courseId: Long, number: Int): Observable<Question>
}