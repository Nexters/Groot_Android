package com.nexters.android.pliary.view.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Decorations {
    companion object
}



fun Decorations.Companion.startOffset(px: Int = 0) : RecyclerView.ItemDecoration =
    object : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            if( parent.layoutManager is LinearLayoutManager) {
                if( parent.getChildAdapterPosition(view) < 1 ) {
                    if( (parent.layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.HORIZONTAL ) {
                        outRect.left = px
                    } else {
                        outRect.top = px
                    }
                }
            }
        }
    }

fun Decorations.Companion.endOffset(px: Int = 0) : RecyclerView.ItemDecoration =
    object : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            if( parent.layoutManager is LinearLayoutManager) {
                if( parent.getChildAdapterPosition(view) == state.itemCount - 1 ) {
                    if( (parent.layoutManager as LinearLayoutManager).orientation == LinearLayoutManager.HORIZONTAL ) {
                        outRect.right = px
                    } else {
                        outRect.bottom = px
                    }
                }
            }
        }
    }

fun Decorations.Companion.itemSpacing(leftPx: Int = 0,
                                      topPx: Int = 0,
                                      rightPx: Int = 0,
                                      bottomPx: Int = 0) =
    object : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            with(outRect) {
                left = leftPx
                top = topPx
                right = rightPx
                bottom = bottomPx
            }
        }
    }
