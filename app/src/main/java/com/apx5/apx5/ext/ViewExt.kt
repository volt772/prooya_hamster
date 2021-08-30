package com.apx5.apx5.ext

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.apx5.apx5.ProoyaClient.Companion.appContext
import com.apx5.apx5.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * ViewExt
 */
fun showProgressDialog(context: Context): Dialog {
    val builder = AlertDialog.Builder(context)
    builder.setView(R.layout.progress_dialog)
    return builder.create()
}

/**
 * UI 화면노출 유무
 * @param isVisible Boolean
 * @return View
 */
fun setVisibility(isVisible: Boolean): Int {
    return if (isVisible) View.VISIBLE else View.GONE
}

/**
 * setSystemBarColor
 */
fun setSystemBarColor(act: Activity, @ColorRes color: Int) {
    act.window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        statusBarColor = act.resources.getColor(color)
    }
}

/**
 * displayImageRound
 */
fun displayImageRound(ctx: Context, img: ImageView, @DrawableRes drawable: Int) {
    Glide
        .with(ctx)
        .load(drawable)
        .apply(RequestOptions.circleCropTransform())
        .into(img)
}

/**
 * Get Drawable
 */
fun drawableRes(drawable: Int) = ContextCompat.getDrawable(appContext, drawable)
