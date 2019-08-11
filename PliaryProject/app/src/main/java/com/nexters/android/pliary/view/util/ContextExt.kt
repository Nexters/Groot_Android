package com.nexters.android.pliary.view.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager

fun Context.dpToPx(dp: Int) : Int =
    (dp * resources.displayMetrics.density).toInt()

fun getScreenWidth(context: Context): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

internal val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

internal val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()