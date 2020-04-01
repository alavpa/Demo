package com.alavpa.demo.android

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

abstract class DemoNavigation {
    private var activity: AppCompatActivity? = null
    fun attach(activity: AppCompatActivity) {
        this.activity = activity
    }

    protected fun startActivity(cls: Class<*>, extras: Bundle? = null) {
        activity?.let {
            Intent(it, cls).apply { if (extras != null) this.putExtras(extras) }
        }?.also { startActivity(it) }
    }

    protected fun startActivity(intent: Intent) {
        activity?.run { this.startActivity(intent) }
    }

    protected fun finishActivity() {
        activity?.finish()
    }

    fun detach() {
        activity = null
    }

    fun showAlertDialog(messageId: Int, buttonId: Int, onClick: DialogInterface.OnClickListener) {
        activity?.run {
            AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(messageId)
                .setNeutralButton(buttonId, onClick)
                .create().also {
                    DemoDialogFragment.newInstance(it).show(this)
                }
        }
    }
}
