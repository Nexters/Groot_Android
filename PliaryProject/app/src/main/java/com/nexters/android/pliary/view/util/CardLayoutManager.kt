package com.nexters.android.pliary.view.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

internal class CardLayoutManager(context: Context?) : LinearLayoutManager(context, HORIZONTAL, false) {

    companion object {
        private const val SHRINK_SCALE_AMOUNT = 0.1F
        private const val SHRINK_DISTANCE = 0.9F
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return when (orientation) {
            HORIZONTAL -> {
                val scrolled = super.scrollHorizontallyBy(dx, recycler, state)

                val midpoint = width / 2F

                val percentage = SHRINK_DISTANCE * midpoint

                for (i in 0 until childCount) {
                    val child = getChildAt(i) ?: return 0

                    val childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2F

                    val distance = Math.min(percentage, Math.abs(midpoint - childMidpoint))

                    val scale = 1F + (-SHRINK_SCALE_AMOUNT) * (distance) / (percentage)

                    child.scaleX = scale
                    child.scaleY = scale
                }

                scrolled
            }
            else -> {
                0
            }
        }
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        scrollHorizontallyBy(0, recycler, state)

        return super.scrollVerticallyBy(dy, recycler, state)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)

        scrollHorizontallyBy(0, recycler, state)
    }
}