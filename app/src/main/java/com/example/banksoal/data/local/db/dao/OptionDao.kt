package com.example.banksoal.data.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.banksoal.data.model.db.Option

@Dao
interface OptionDao : BaseDao<Option> {
    @Query("SELECT * FROM options")
    override fun loadAll(): List<Option>

    @Query("SELECT * FROM options WHERE question_id = :questionId")
    fun loadAllByQuestionId(questionId: Long?): List<Option>

    @Query("DELETE FROM questions WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM options")
    override fun truncate()
}