package com.alavpa.demo.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.alavpa.demo.data.database.dao.base.BaseDao
import com.alavpa.demo.data.database.model.UserEntity
import io.reactivex.Maybe

@Dao
abstract class UserDao : BaseDao<UserEntity>() {
    @Query("SELECT * FROM UserEntity WHERE id = :id")
    abstract fun get(id: Long): Maybe<UserEntity>
}
