package com.alavpa.demo.presentation.user

data class UserViewModel(
    val showLoading: Boolean = false,
    val title: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val website: String = ""
)
