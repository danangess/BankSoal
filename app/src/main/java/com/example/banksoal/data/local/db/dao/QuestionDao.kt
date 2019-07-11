package com.example.banksoal.data.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.banksoal.data.model.db.Question

@Dao
interface QuestionDao : BaseDao<Question> {
    @Query("SELECT * FROM questions")
    override fun loadAll(): List<Question>

    @Query("SELECT * FROM questions WHERE course_id = :courseId")
    fun loadAllByCourseId(courseId: Long): List<Question>

    @Query("DELETE FROM questions WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM questions")
    override fun truncate()
}