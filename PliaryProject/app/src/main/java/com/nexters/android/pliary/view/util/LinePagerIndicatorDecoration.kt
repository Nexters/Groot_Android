package com.nexters.android.pliary.view.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.android.pliary.R

class LinePagerIndicatorDecoration(val context : Context, val count : Int) : RecyclerView.ItemDecoration() {

    private val colorActive = ResUtils.getColor(context, R.color.green)
    private val colorInactive = ResUtils.getColor(context, R.color.gray3)

    companion object { private val DP = Resources.getSystem().displayMetrics.density }

    /**
     * Height of the space the indicator takes up at the bottom of the view.
     */
    private val mIndicatorHeight = (DP * 40).toInt()

    /**
     * Indicator stroke width.
     */
    private val mIndicatorStrokeWidth = DP * 5

    /**
     * Indicator width.
     */
    private val maxLength = DP * 268
    /**
     * Padding between indicators.
     */
    private val mIndicatorItemPadding = 0

    /**
     * Some more natural animation interpolation
     */
    private val mInterpolator = AccelerateDecelerateInterpolator()

    private val mPaint = Paint()

    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount = count


        // center horizontally, calculate width and subtract half from center
        val totalLength = maxLength
        val paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        // center vertically in the allotted space
        val indicatorPosY = parent.height + - mIndicatorHeight / 2f

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY)


        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val activePosition = layoutManager.findFirstVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager.findViewByPosition(activePosition)
        var left = activeChild?.left ?: 0
        val width = activeChild?.width ?: 0

        if(count == 1) {
            left += - context.dpToPx(35)
        } else {
            left += - context.dpToPx(50)
        }

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        val progress = mInterpolator.getInterpolation(left * -1 / width.toFloat())

        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount)
    }

    private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float, indicatorPosY: Float) {
        mPaint.color = colorInactive
        c.drawLine(indicatorStartX, indicatorPosY, indicatorStartX + maxLength, indicatorPosY, mPaint)

    }

    private fun drawHighlights(c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
                               highlightPosition: Int, progress: Float, itemCount: Int) {
        mPaint.color = colorActive

        // width of item indicator including padding
        val itemWidth = if(count != 0) (maxLength / count) else maxLength

        if (progress == 0f) {
            // no swipe, draw a normal indicator
            c.drawLine(indicatorStartX, indicatorPosY,
                indicatorStartX + itemWidth, indicatorPosY, mPaint)
        } else {
            var highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength = itemWidth * progress

            // draw the cut off highlight
            c.drawLine(highlightStart + partialLength, indicatorPosY,
                highlightStart + itemWidth, indicatorPosY, mPaint)

            // draw the highlight overlapping to the next item as well
            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth
                c.drawLine(highlightStart, indicatorPosY,
                    highlightStart + partialLength, indicatorPosY, mPaint)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = mIndicatorHeight
    }


}