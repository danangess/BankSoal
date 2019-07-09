package com.example.banksoal.data.local.db

import com.example.banksoal.data.local.db.dao.CourseDao
import com.example.banksoal.data.model.db.Course
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCourseRepository
@Inject
constructor(private val appDatabase: AppDatabase) : AppBaseRepository<CourseDao, Course, Long>(appDatabase.courseDao),
    CourseRepository {
    override fun get(id: Long): Single<Course> {
        return Single.fromCallable {
            appDatabase.courseDao.get(id)
        }
    }

    override fun remove(tKey: Long): Single<Boolean> {
        return Single.fromCallable {
            appDatabase.courseDao.delete(tKey)
            true
        }
    }
}