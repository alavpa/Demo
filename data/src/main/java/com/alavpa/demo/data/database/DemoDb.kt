package com.alavpa.demo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alavpa.demo.data.database.DemoDb.Companion.DB_VERSION
import com.alavpa.demo.data.database.dao.CommentDao
import com.alavpa.demo.data.database.dao.PostDao
import com.alavpa.demo.data.database.dao.UserDao
import com.alavpa.demo.data.database.model.CommentEntity
import com.alavpa.demo.data.database.model.PostEntity
import com.alavpa.demo.data.database.model.UserEntity

@Database(
    entities = [
        PostEntity::class,
        UserEntity::class,
        CommentEntity::class
    ],
    exportSchema = false,
    version = DB_VERSION
)
abstract class DemoDb : RoomDatabase() {
    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "Demo.db"
    }

    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao
}
