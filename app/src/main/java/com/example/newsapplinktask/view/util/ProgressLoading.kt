package com.example.newsapplinktask.view.util

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.example.newsapplinktask.R

object ProgressLoading {
    private var progress: Dialog? = null

    private var hasActivity: Boolean = false
    private fun init(activity: Activity) {
        progress = Dialog(activity)
        progress?.setContentView(R.layout.progress_loading_layout)
        progress?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progress?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    } // init

    fun show(activity: Activity) {

        if (!hasActivity) {
            hasActivity = true
            init(activity)
        }

        if (!(activity).isFinishing) {
            try {
                progress?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    } // show

    fun isVisible(): Boolean {
        return try {
            progress!!.isShowing
        } catch (e: Exception) {
            false
        }
    } // fun of isVisible

    fun dismiss() {
        if (isVisible()) {
            progress?.dismiss()
            hasActivity = false
        }
    } // dismiss
} // class of ProgressLoading