package com.example.banksoal.data.model

import com.example.banksoal.data.model.db.Course
import com.example.banksoal.data.model.db.Question

class CourseData(val course: Course) {
    override fun toString(): String = course.name
}