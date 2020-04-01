package com.alavpa.demo.android

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

class DemoDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(demoDialog: Dialog): DemoDialogFragment {
            return DemoDialogFragment().apply {
                this.demoDialog = demoDialog
            }
        }
    }

    private lateinit var demoDialog: Dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return demoDialog
    }

    fun show(activity: FragmentActivity?) {
        activity?.run {
            show(this.supportFragmentManager, "DialogFragment")
        }
    }
}
