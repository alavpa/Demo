package com.alavpa.demo.data.database.dao.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import io.reactivex.Completable

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAction(table: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAllAction(tableList: List<T>): List<Long>

    @Update
    abstract fun updateAction(table: T)

    @Update
    abstract fun updateAllAction(tableList: List<T>)

    fun upsert(table: T): Completable {
        return Completable.fromAction { upsertAction(table) }
    }

    fun upsertAll(tableList: List<T>): Completable {
        return Completable.fromAction { upsertAllAction(tableList) }
    }

    @Transaction
    private fun upsertAction(table: T) {
        if (insertAction(table) == -1L) {
            updateAction(table)
        }
    }

    @Transaction
    private fun upsertAllAction(tableList: List<T>) {
        val res = insertAllAction(tableList)
        val updateItems = res.mapIndexed { index, result -> Pair(index, result) }
            .filter { it.second == -1L }
            .map { tableList[it.first] }

        if (updateItems.isNotEmpty()) {
            updateAllAction(updateItems)
        }
    }
}
