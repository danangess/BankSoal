package com.example.banksoal.data.local.db

import com.example.banksoal.data.model.db.Course
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCourseRepository
@Inject
constructor(private val appDatabase: AppDatabase): AppBaseRepository<Course, Long>(appDatabase.courseDao()), CourseRepository {
    override fun remove(tKey: Long): Single<Boolean> {
        return Single.fromCallable {
            appDatabase.courseDao().delete(tKey)
            true
        }
    }
}