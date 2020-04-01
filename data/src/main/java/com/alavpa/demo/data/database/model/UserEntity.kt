package com.alavpa.demo.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val id: Long = 0,
    val name: String = "",
    val username: String = "",
    val phone: String = "",
    val email: String = "",
    val website: String = ""
)
