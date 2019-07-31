package com.nexters.android.pliary.view.util

import android.content.Context

fun Context.dpToPx(dp: Int) : Int =
    (dp * resources.displayMetrics.density).toInt()