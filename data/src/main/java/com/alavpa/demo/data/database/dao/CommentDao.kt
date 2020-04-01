package com.alavpa.demo.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.alavpa.demo.data.database.dao.base.BaseDao
import com.alavpa.demo.data.database.model.CommentEntity
import io.reactivex.Single

@Dao
abstract class CommentDao : BaseDao<CommentEntity>() {
    @Query("SELECT * FROM CommentEntity WHERE postId = :postId")
    abstract fun get(postId: Long): Single<List<CommentEntity>>
}
