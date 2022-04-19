package com.example.myapplication.ui.base

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

open class BaseActivity : AppCompatActivity() {

    var errorDialog: AlertDialog? = null

    open fun showError(message: String?) {
        errorDialog = AlertDialog.Builder(this)
            .setMessage(if (message.isNullOrBlank()) resources.getString(R.string.app_error) else message)
            .setNegativeButton(resources.getString(R.string.dismiss), null)
            .show()
    }
}