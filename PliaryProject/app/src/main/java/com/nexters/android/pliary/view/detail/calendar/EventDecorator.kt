package com.nexters.android.pliary.view.detail.calendar

import android.content.Context
import android.graphics.drawable.Drawable
import com.nexters.android.pliary.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan


class EventDecorator(private val shape: Int, dates: Collection<CalendarDay>, val context: Context) :
    DayViewDecorator {

    companion object {
        const val BG_CIRCLE_BLUE = 0
        const val BG_CIRCLE_GREEN = 1
        const val UNDER_DOT = 2
    }

    private val drawable: Drawable = context.resources.getDrawable(R.drawable.more)
    private val blueCircle: Drawable = context.resources.getDrawable(R.drawable.calendar_date_blue)
    private val greenCircle: Drawable = context.resources.getDrawable(R.drawable.calendar_date_green)
    private val dates: HashSet<CalendarDay> = HashSet(dates)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        when(shape) {
            BG_CIRCLE_BLUE -> view.setSelectionDrawable(blueCircle)
            BG_CIRCLE_GREEN -> view.setSelectionDrawable(greenCircle)
            UNDER_DOT -> view.addSpan( DotSpan(5f, context.resources.getColor(R.color.gray1))) // 날자밑에 점
        }


    }
}