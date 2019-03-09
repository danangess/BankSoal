package com.example.banksoal.data.local.db

import com.example.banksoal.data.local.db.dao.UserDao
import com.example.banksoal.data.model.db.User
import io.reactivex.Single

interface UserRepository: BaseRepository<UserDao, User, Long> {
    fun validate(username: String, password: String): Single<Boolean>
    fun remove(username: String): Single<Boolean>
}