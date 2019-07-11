package com.example.banksoal.data.local.db

import com.example.banksoal.data.local.db.dao.BaseDao
import io.reactivex.Observable
import io.reactivex.Single

abstract class AppBaseRepository<TDao, TEntity, TKey>
constructor(val baseDao: BaseDao<TEntity>) : BaseRepository<TDao, TEntity, TKey> {
    @Suppress("UNCHECKED_CAST")
    override val dao: TDao = baseDao as TDao

    override fun getAll(): Observable<List<TEntity>> {
        return Observable.fromCallable { baseDao.loadAll() }
    }

    override fun add(entity: TEntity): Single<Boolean> {
        return Single.fromCallable {
            baseDao.insert(entity)
            true
        }
    }

    override fun add(entities: List<TEntity>): Observable<Boolean> {
        return Observable.fromCallable {
            baseDao.insert(entities)
            true
        }
    }

    override fun edit(entity: TEntity): Single<Boolean> {
        return Single.fromCallable {
            baseDao.update(entity)
            true
        }
    }

    override fun truncate(): Single<Boolean> {
        return Single.fromCallable {
            baseDao.truncate()
            true
        }
    }
}