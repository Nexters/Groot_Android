package com.nexters.android.pliary.view.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CardItemDecoration(val gapHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = gapHeight.toPx
    }
}