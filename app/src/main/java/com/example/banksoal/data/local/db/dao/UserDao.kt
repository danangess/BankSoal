package com.example.banksoal.data.local.db.dao

import android.arch.persistence.room.*
import com.example.banksoal.data.model.db.User

@Dao
interface UserDao : BaseDao<User> {
    @Query("DELETE FROM users WHERE username = :username")
    fun delete(username: String)

    @Query("DELETE FROM users WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM users WHERE username = :username")
    fun findByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun findByUsernameAndPassword(username: String, password: String): User?

    @Query("SELECT * FROM users")
    override fun loadAll(): List<User>

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: List<Int>): List<User>

    @Query("DELETE FROM users")
    override fun truncate()
}