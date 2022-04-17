package com.example.myapplication.ui.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R


open class BaseFragment : DialogFragment() {

    private var errorDialog: AlertDialog? = null

    private val progress: ProgressDialog by lazy {
        val progress = ProgressDialog(context)
        progress.setCancelable(false)
        progress
    }

    fun showProgress(message: String = resources.getString(R.string.loading)) {
        if (!progress.isShowing) {
            progress.setMessage(message)
            progress.show()
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
            .setMessage(if (message.isNullOrBlank()) resources.getString(R.string.app_error) else message)
            .setNegativeButton(resources.getString(R.string.dismiss), null)
            .show()
    }

    open fun showAction(message: String?, isCancellable: Boolean = false, action: () -> Unit) {
        errorDialog = AlertDialog.Builder(context)
            .setMessage(if (message.isNullOrBlank()) resources.getString(R.string.app_error) else message)
            .setNegativeButton(resources.getString(R.string.dismiss), null)
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