package com.nexters.android.pliary.view.util

import com.prolificinteractive.materialcalendarview.CalendarDay


fun today() = CalendarDay.today().toString()
fun CalendarDay.isToday() = this == CalendarDay.today()