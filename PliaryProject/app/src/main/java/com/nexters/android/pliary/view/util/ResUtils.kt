package com.nexters.android.pliary.view.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat

class ResUtils {

    companion object {
        fun getColor(context: Context, id: Int): Int {
            return  if (Build.VERSION.SDK_INT >= 23) {
                ContextCompat.getColor(context, id)
            } else {
                context.resources.getColor(id)
            }

        }

        fun getDrawable(context: Context, res: Int): Drawable? {
            return if (Build.VERSION.SDK_INT >= 21) ContextCompat.getDrawable(context, res)
            else context.resources.getDrawable(res)
        }
    }

}