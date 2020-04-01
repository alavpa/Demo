package com.alavpa.demo.presentation.main

import com.alavpa.demo.domain.model.Post

data class MainViewModel(
    val showLoading: Boolean = false,
    val posts: List<Post> = listOf(),
    val page: Int = 0
)
