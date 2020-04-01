package com.alavpa.demo.presentation.detail

interface PostDetailNavigation {
    fun goToUser(id: Long)
    fun finish()
    fun showError(onClose: () -> Unit)
}
