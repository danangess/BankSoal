package com.example.banksoal.data.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.banksoal.data.model.db.Question

@Dao
interface QuestionDao : BaseDao<Question> {
    @Query("SELECT * FROM questions ORDER BY id")
    override fun loadAll(): List<Question>

    @Query("SELECT * FROM questions WHERE course_id = :courseId ORDER BY id")
    fun loadAllByCourseId(courseId: Long): List<Question>

    @Query("SELECT * FROM questions WHERE course_id = :courseId ORDER BY id LIMIT 1 OFFSET :number - 1")
    fun loadAllByCourseId(courseId: Long, number: Int): Question

    @Query("DELETE FROM questions WHERE id = :id")
    fun delete(id: Long)

    @Query("DELETE FROM questions")
    override fun truncate()
}