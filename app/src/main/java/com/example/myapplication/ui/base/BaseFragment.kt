package com.example.myapplication.ui.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment


open class BaseFragment : DialogFragment() {


    var isNavigateUpEnabled = true  //  The back option item in the actionbar

    var errorDialog: AlertDialog? = null

    private val progress: ProgressDialog by lazy {
        val progress = ProgressDialog(context)
        progress.setCancelable(false)
        progress
    }

    fun showProgress(message: String = "Performing action") {
        if (!progress.isShowing) {
            progress.setMessage(message)
            progress.show()
        }

        val llPadding = 30
        val ll = LinearLayout(activity)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(activity)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(activity)
        tvText.text = "Loading ..."
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20f
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        dialog.show()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }

    fun hideProgress() {
        if (progress.isShowing) {
            progress.dismiss()
        }
    }

    open fun showSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    open fun showError(message: String?) {
        errorDialog = AlertDialog.Builder(context)
            .setMessage(if (message.isNullOrBlank()) "Undefined error" else message)
            .setNegativeButton("Dismiss", null)
            .show()
    }

    open fun showAction(message: String?, isCancellable: Boolean = false, action: () -> Unit) {
        errorDialog = AlertDialog.Builder(context)
            .setMessage(if (message.isNullOrBlank()) "Undefined error" else message)
            .setNegativeButton("Dismiss", null)
            .setOnDismissListener { action() }
            .setCancelable(isCancellable)
            .show()
    }

    open fun showError(throwable: Throwable) {
        showError("${throwable.javaClass.simpleName}\n\n${throwable.localizedMessage}")
    }

    open fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        errorDialog?.dismiss()
    }

}