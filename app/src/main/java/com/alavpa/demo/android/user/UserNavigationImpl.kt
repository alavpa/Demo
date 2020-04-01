package com.alavpa.demo.android.user

import android.content.Intent
import android.net.Uri
import com.alavpa.demo.android.DemoNavigation
import com.alavpa.demo.presentation.user.UserNavigation

class UserNavigationImpl : UserNavigation, DemoNavigation() {
    override fun finish() {
        this.finishActivity()
    }

    override fun goToWeb(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://$url")))
    }

    override fun goToEmail(email: String) {
        Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }.also {
            startActivity(it)
        }
    }

    override fun goToPhone(phone: String) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
    }
}
