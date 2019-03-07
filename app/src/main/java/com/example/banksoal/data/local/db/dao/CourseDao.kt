package com.example.banksoal.data.local.db.dao

import android.arch.persistence.room.*
import com.example.banksoal.data.model.db.Course

@Dao
interface CourseDao: BaseDao<Course> {
    @Query("SELECT * FROM courses")
    override fun loadAll(): List<Course>

    @Query("DELETE FROM courses WHERE id = :id")
    fun delete(id: Long)
}