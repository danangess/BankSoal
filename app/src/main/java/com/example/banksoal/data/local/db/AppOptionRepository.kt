package com.example.banksoal.data.local.db

import com.example.banksoal.data.model.db.Option
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppOptionRepository
@Inject
constructor(private val appDatabase: AppDatabase) : AppBaseRepository<Option, Long>(appDatabase.optionDao()), OptionRepository {
    override fun remove(tKey: Long): Single<Boolean> {
        return Single.fromCallable {
            appDatabase.optionDao().delete(tKey)
            true
        }
    }

    override fun getAllByQuestionId(questionId: Long): Observable<List<Option>> {
        return Observable.fromCallable {
            appDatabase.optionDao().loadAllByQuestionId(questionId)
        }
    }
}