package com.example.banksoal.data.local.db

import com.example.banksoal.data.local.db.dao.OptionDao
import com.example.banksoal.data.model.db.Option
import io.reactivex.Observable

interface OptionRepository: BaseRepository<OptionDao, Option, Long> {
    fun getAllByQuestionId(questionId: Long): Observable<List<Option>>
}