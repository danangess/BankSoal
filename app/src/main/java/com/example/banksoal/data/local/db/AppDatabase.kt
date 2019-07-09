package com.example.banksoal.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.banksoal.data.model.db.*
import com.example.banksoal.data.local.db.dao.*

@Database(
    entities = [
        Course::class,
        Question::class,
        Option::class,
        User::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val courseDao: CourseDao
    abstract val questionDao: QuestionDao
    abstract val optionDao: OptionDao
    abstract val userDao: UserDao
}