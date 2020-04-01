package com.alavpa.demo.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentEntity(
    @PrimaryKey
    val id: Long = 0,
    val postId: Long = 0,
    val name: String = "",
    val email: String = "",
    val body: String = ""
)
