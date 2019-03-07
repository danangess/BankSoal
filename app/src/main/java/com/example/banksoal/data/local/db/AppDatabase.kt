package com.example.banksoal.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.banksoal.data.model.db.*
import com.example.banksoal.data.local.db.dao.*

@Database(
    entities = [
        User::class,
        Course::class,
        Question::class,
        Option::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun questionDao(): QuestionDao
    abstract fun optionDao(): OptionDao
    abstract fun userDao(): UserDao
}