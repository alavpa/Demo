package com.alavpa.demo.domain.model

data class Comment(
    val id: Long = 0,
    val postId: Long = 0,
    val title: String = "",
    val email: String = "",
    val body: String = ""
)
