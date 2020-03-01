package com.apx5.apx5.ui.utils

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object MaterialTools {
    fun setSystemBarColor(act: Activity, @ColorRes color: Int) {
        val window = act.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = act.resources.getColor(color)
    }

    fun displayImageRound(ctx: Context, img: ImageView, @DrawableRes drawable: Int) {
        Glide
            .with(ctx)
            .load(drawable)
            .apply(RequestOptions.circleCropTransform())
            .into(img)
    }
}
