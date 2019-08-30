package com.nexters.android.pliary.view.detail.calendar.fragment

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.detail.calendar.EventDecorator
import com.nexters.android.pliary.view.detail.calendar.viewmodel.DetailCalendarViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import kotlinx.android.synthetic.main.fragment_calendar_layout.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors


class DetailCalendarFragment : BaseFragment<DetailCalendarViewModel>() {

    override fun getModelClass(): Class<DetailCalendarViewModel> = DetailCalendarViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.nexters.android.pliary.R.layout.fragment_calendar_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        calendarView.state().edit()
            .setFirstDayOfWeek(DayOfWeek.SUNDAY)
            .setMinimumDate(CalendarDay.today().date.minusMonths(2))
            .setMaximumDate(CalendarDay.today().date.plusMonths(2))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        val result = arrayOf("2019,08,18", "2019,07,18", "2019,08,19", "2019,08,08")

        ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor())

    }

    private inner class ApiSimulator internal constructor(internal var Time_Result: Array<String>) :
        AsyncTask<Void, Void, List<CalendarDay>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            val dates = arrayListOf<CalendarDay>()

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for (i in Time_Result.indices) {

                val day = CalendarDay.from(LocalDate.from(DateTimeFormatter.ofPattern("yyyy,MM,dd").parse(Time_Result[i])))
                val time = Time_Result[i].split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val year = Integer.parseInt(time[0])
                val month = Integer.parseInt(time[1])
                val dayy = Integer.parseInt(time[2])

                dates.add(day)
                calendar.set(year, month - 1, dayy)
            }



            return dates
        }

        override fun onPostExecute(calendarDays: List<CalendarDay>) {
            super.onPostExecute(calendarDays)


            calendarView.addDecorator(
                this@DetailCalendarFragment.context?.let {
                    EventDecorator(
                        Color.GREEN,
                        calendarDays,
                        it
                    )
                }
            )
        }
    }
}