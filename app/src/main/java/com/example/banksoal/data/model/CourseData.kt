package com.example.banksoal.data.model

import com.example.banksoal.data.model.db.Course
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CourseData(val course: Course) {
    fun getCompetences(): List<String> {
        val gson = Gson()
        return gson.fromJson(course.competences, object : TypeToken<List<String>>() {}.type)
    }

    override fun toString(): String = course.name
}