package com.alavpa.demo.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity(
    @PrimaryKey
    val id: Long = 0,
    val userId: Long = 0,
    val title: String = "",
    val body: String = ""
)
