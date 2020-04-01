package com.alavpa.demo.presentation.user

interface UserNavigation {
    fun finish()
    fun goToWeb(url: String)
    fun goToEmail(email: String)
    fun goToPhone(phone: String)
}
