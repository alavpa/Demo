package com.alavpa.demo.presentation.detail

import com.alavpa.demo.domain.model.Comment

data class PostDetailViewModel(
    val showLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val authorId: Long = 0,
    val author: String = "",
    val commentsNumber: Int = 0,
    val comments: List<Comment> = listOf(),
    val showComments: Boolean = false,
    val page: Int = 0
)
