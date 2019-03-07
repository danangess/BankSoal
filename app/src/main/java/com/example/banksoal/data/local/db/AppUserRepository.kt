package com.example.banksoal.data.local.db

import com.example.banksoal.data.model.db.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUserRepository
@Inject
constructor(private val appDatabase: AppDatabase) : AppBaseRepository<User, Long>(appDatabase.userDao()), UserRepository {
    override fun remove(username: String): Single<Boolean> {
        return Single.fromCallable {
            appDatabase.userDao().delete(username)
            true
        }
    }

    override fun remove(tKey: Long): Single<Boolean> {
        return Single.fromCallable {
            appDatabase.userDao().delete(tKey)
            true
        }
    }

    override fun validate(username: String, password: String): Single<Boolean> {
        return Single.fromCallable {
            val user : User? = appDatabase.userDao().loadAll().singleOrNull { user -> user.username == username && user.password == password }
            user != null
        }
    }
}