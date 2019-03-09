package com.example.banksoal.data.local.db.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

interface BaseDao<TEntity> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tEntity: TEntity)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tEntities: List<TEntity>)

    @Update
    fun update(tEntity: TEntity)
    @Update
    fun update(tEntities: List<TEntity>)

    @Delete
    fun delete(tEntity: TEntity)
    @Delete
    fun delete(tEntities: List<TEntity>)

    fun loadAll(): List<TEntity>
}