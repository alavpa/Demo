package com.alavpa.demo.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.alavpa.demo.data.database.dao.base.BaseDao
import com.alavpa.demo.data.database.model.PostEntity
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class PostDao : BaseDao<PostEntity>() {
    @Query("SELECT * FROM PostEntity LIMIT :limit OFFSET :start")
    abstract fun get(start: Int, limit: Int): Single<List<PostEntity>>

    @Query("SELECT * FROM PostEntity WHERE id = :id")
    abstract fun get(id: Long): Maybe<PostEntity>
}
