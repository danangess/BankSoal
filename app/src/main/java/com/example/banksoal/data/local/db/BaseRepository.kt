package com.example.banksoal.data.local.db

import io.reactivex.Observable
import io.reactivex.Single

interface BaseRepository<TEntity, TKey> {
    fun getAll(): Observable<List<TEntity>>
    fun add(entity: TEntity): Single<Boolean>
    fun add(entities: List<TEntity>): Observable<Boolean>
    fun edit(entity: TEntity): Single<Boolean>
    fun remove(tKey: TKey): Single<Boolean>
}