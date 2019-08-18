package com.nexters.android.pliary.view.util

import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*


fun today() = CalendarDay.today().toString()
fun CalendarDay.isToday() = this == CalendarDay.today()

fun String.toZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA))
}

fun ZonedDateTime.yyyyMMdd(): String {
    return DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA)
        .format(this)
}

fun ZonedDateTime.HHmm(): String {
    return DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA).format(this)
}

fun ZonedDateTime.weekDay(): String {
    return this.dayOfWeek.weekDay()
}

fun DayOfWeek.weekDay(): String {
    val days = listOf("월", "화", "수", "목", "금", "토", "일")

    return days[this.value - 1]
}