package com.nexters.android.pliary.view.detail.calendar

import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.CalendarDay
import android.content.Context
import android.graphics.drawable.Drawable
import com.nexters.android.pliary.R
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.spans.DotSpan


class EventDecorator(private val color: Int, dates: Collection<CalendarDay>, context: Context) :
    DayViewDecorator {

    private val drawable: Drawable = context.resources.getDrawable(R.drawable.more)
    private val dates: HashSet<CalendarDay> = HashSet(dates)

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
        view.addSpan( DotSpan(5f, color)) // 날자밑에 점
    }
}