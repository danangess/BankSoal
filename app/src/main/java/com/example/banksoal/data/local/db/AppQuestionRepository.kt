package com.example.banksoal.data.local.db

import com.example.banksoal.data.model.db.Question
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppQuestionRepository
@Inject
constructor(private val appDatabase: AppDatabase) : AppBaseRepository<Question, Long>(appDatabase.questionDao()), QuestionRepository {
    override fun remove(tKey: Long): Single<Boolean> {
        return Single.fromCallable {
            appDatabase.questionDao().delete(tKey)
            true
        }
    }

    override fun getAllByCourseId(courseId: Long): Observable<List<Question>> {
        return Observable.fromCallable {
            appDatabase.questionDao().loadAllByCourseId(courseId)
        }
    }
}