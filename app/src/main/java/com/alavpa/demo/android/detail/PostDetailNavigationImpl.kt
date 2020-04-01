package com.alavpa.demo.android.detail

import android.content.DialogInterface
import android.os.Bundle
import com.alavpa.demo.R
import com.alavpa.demo.android.DemoNavigation
import com.alavpa.demo.android.user.UserActivity
import com.alavpa.demo.presentation.detail.PostDetailNavigation

class PostDetailNavigationImpl : PostDetailNavigation, DemoNavigation() {
    override fun goToUser(id: Long) {
        startActivity(UserActivity::class.java, Bundle().apply { putLong(UserActivity.EXTRA_ID, id) })
    }

    override fun finish() {
        this.finishActivity()
    }

    override fun showError(onClose: () -> Unit) {
        showAlertDialog(
            R.string.detail_message_error,
            android.R.string.ok,
            DialogInterface.OnClickListener { dialog, _ ->
                onClose()
                dialog.dismiss()
            }
        )
    }
}
