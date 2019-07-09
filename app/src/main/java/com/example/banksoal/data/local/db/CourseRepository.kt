package com.example.banksoal.data.local.db

import com.example.banksoal.data.local.db.dao.CourseDao
import com.example.banksoal.data.model.db.Course
import io.reactivex.Single

interface CourseRepository : BaseRepository<CourseDao, Course, Long> {
    fun get(id: Long): Single<Course>
}