package com.alavpa.demo.domain.model

data class PostDetail(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val user: User = User()
)
