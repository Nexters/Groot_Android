package com.nexters.android.pliary.view.util

import android.content.Context
import android.content.res.Resources
import android.transition.Transition
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nexters.android.pliary.R

fun Context.dpToPx(dp: Int) : Int =
    (dp * resources.displayMetrics.density).toInt()

fun getScreenWidth(context: Context): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

internal val Int.toPx: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

internal val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun ImageView.setGIF(url: String?) {
    url?.apply {
        Glide.with(this@setGIF.context)
            .asGif()
            .load("https://dailyissue.s3.ap-northeast-2.amazonaws.com/${this}.gif")
            .placeholder(R.drawable.and_posi_placeholer)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(this@setGIF)
    }
}