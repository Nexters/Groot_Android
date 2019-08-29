package com.nexters.android.pliary.view.util

import com.nexters.android.pliary.db.converter.ZonedDateTimeConverter.Companion.ISO8601
import com.nexters.android.pliary.db.converter.ZonedDateTimeConverter.Companion.ZONE_SEOUL
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.util.*


fun today() : String = CalendarDay.today().run { "${date.year}.${date.monthValue}.${date.dayOfMonth}" }
fun CalendarDay.isToday() = this == CalendarDay.today()

fun todayValue() : String = ZonedDateTime.now(ZONE_SEOUL).yyyyMMdd()

fun String.toZonedDateTime(): ZonedDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd'T'HH:mm:ss.SSSX").parse("${this}T00:10:35.741+09")


    return ZonedDateTime.from(formatter).withZoneSameInstant(ZONE_SEOUL)
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

fun String?.getFutureWateringDate(dayTerm: Int = 1) : String{
    return if(this.isNullOrEmpty()) {
        ZonedDateTime.now(ZONE_SEOUL).plusDays(dayTerm.toLong()).yyyyMMdd()
    } else {
        this.toZonedDateTime().plusDays(dayTerm.toLong()).yyyyMMdd()
    }
}

fun isWateredDayPast(willbeWatering: String) : Boolean {
    if(willbeWatering.isNullOrEmpty()) return false

    val last = willbeWatering.toZonedDateTime()//.plusDays(dayTerm.toLong())
    val result = calculateDDay(last)
    return when {
        result < 0 -> false
        else -> true
    }
}

fun getFirstMetDDay(takeDate: String) : Int {
    val take = takeDate.toZonedDateTime()
    return calculateDDay(take).toInt()
}

fun getWateredDDay(willbeWatering: String) : String {
    if(willbeWatering.isNullOrEmpty()) return ""
    val last = willbeWatering.toZonedDateTime()//.plusDays(dayTerm.toLong())
    return last.getDday()
}

fun ZonedDateTime.getDday(): String {

    val result = calculateDDay(this)

    // 출력 시 d-day 에 맞게 표시
    val strFormat: String
    if(result > 0) {
        strFormat = "D+%d"
    } else if (result == 0L) {
        strFormat = "D-Day"
    } else {
        strFormat = "D%d"
    }
    /*if (result > 0) {
        strFormat = "D-%d"
    } else if (result == 0L) {
        strFormat = "D-Day"
    } else {
        result *= -1
        strFormat = "D+%d"
    }*/

    return String.format(strFormat, result)
}

private fun calculateDDay(target: ZonedDateTime) : Long{
    val ONE_DAY = 24 * 60 * 60 * 1000
    // D-day 설정
    /*val ddayCalendar = Calendar.getInstance()
    ddayCalendar.set(a_year, a_monthOfYear, a_dayOfMonth)*/

    // D-day 를 구하기 위해 millisecond 으로 환산하여 d-day 에서 today 의 차를 구한다.
    val dday = target.toInstant().toEpochMilli() / ONE_DAY
    val today = Instant.now().toEpochMilli() / ONE_DAY
    return today - dday
}