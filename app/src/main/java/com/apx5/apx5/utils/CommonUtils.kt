package com.apx5.apx5.utils

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.apx5.apx5.R


/**
 * CommonUtils
 */

object CommonUtils {

    fun showProgressDialog(context: Context): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.progress_dialog)
        return builder.create()
    }
}