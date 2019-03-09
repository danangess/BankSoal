package com.example.banksoal.data.local.db

import com.example.banksoal.data.local.db.dao.CourseDao
import com.example.banksoal.data.model.db.Course

interface CourseRepository: BaseRepository<CourseDao, Course, Long>