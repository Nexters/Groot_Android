package com.nexters.android.pliary.view.detail.calendar.fragment

import android.content.res.Resources
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.detail.calendar.EventDecorator
import com.nexters.android.pliary.view.detail.calendar.EventDecorator.Companion.BG_CIRCLE_BLUE
import com.nexters.android.pliary.view.detail.calendar.EventDecorator.Companion.BG_CIRCLE_GREEN
import com.nexters.android.pliary.view.detail.calendar.EventDecorator.Companion.UNDER_DOT
import com.nexters.android.pliary.view.detail.calendar.viewmodel.DetailCalendarViewModel
import com.nexters.android.pliary.view.main.MainViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import kotlinx.android.synthetic.main.fragment_calendar_layout.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

data class CalendarEventData(
    val shape: Int,
    val dates: ArrayList<CalendarDay>
)

internal class DetailCalendarFragment : BaseFragment<DetailCalendarViewModel>() {

    private lateinit var mainVM : MainViewModel
    private var cardID : Long = -1

    override fun getModelClass(): Class<DetailCalendarViewModel> = DetailCalendarViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar_layout, container, false)
        mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardID = mainVM.cardLiveID

        initView()
        drawEventDates()
    }

    private fun initView() {
        calendarView.state().edit()
            .setFirstDayOfWeek(DayOfWeek.SUNDAY)
            .setMinimumDate(CalendarDay.today().date.minusMonths(2))
            .setMaximumDate(CalendarDay.today().date.plusMonths(2))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()


    }

    private fun drawEventDates() {

        val wateredList = arrayListOf<CalendarDay>()
        if(::mainVM.isInitialized) {
            mainVM.plantLiveData.wateredDays.forEach { wateredList.add(it.toCalendarDay()) }

            // 물 예정 날 / 물 준날
            val datesEvent = arrayOf(
                CalendarEventData(
                    shape = BG_CIRCLE_BLUE,
                    dates = arrayListOf(mainVM.plantLiveData.willbeWateringDate.toCalendarDay())
                ),
                CalendarEventData(
                    shape = BG_CIRCLE_GREEN,
                    dates = wateredList
                )
            )

            ApiSimulator(datesEvent).executeOnExecutor(Executors.newSingleThreadExecutor())
        }
    }

    private fun String.toCalendarDay() : CalendarDay {
        return CalendarDay.from(LocalDate.from(DateTimeFormatter.ofPattern("yyyy.MM.dd").parse(this)))
    }

    private inner class ApiSimulator internal constructor(internal var eventDataList: Array<CalendarEventData>) :
        AsyncTask<Void, Void, List<CalendarEventData>>() {

        override fun doInBackground(vararg voids: Void): List<CalendarEventData> {
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            /*val calendar = Calendar.getInstance()

            *//*특정날짜 달력에 점표시해주는곳*//*
            *//*월은 0이 1월 년,일은 그대로*//*
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
            }*/



            return eventDataList.asList()
        }

        override fun onPostExecute(calendarDays: List<CalendarEventData>) {
            super.onPostExecute(calendarDays)

            calendarView.addDecorator(
                this@DetailCalendarFragment.context?.let {
                    EventDecorator(
                        UNDER_DOT,
                        arrayListOf(CalendarDay.today()),
                        it
                    )
                }
            )

            val willbe = calendarDays.filter {
                it.shape == BG_CIRCLE_BLUE
            }.flatMap { it.dates }

            calendarView.addDecorator(
                this@DetailCalendarFragment.context?.let {
                    EventDecorator(
                        BG_CIRCLE_BLUE,
                        willbe,
                        it
                    )
                }
            )

            var watered = calendarDays.filter { it.shape == BG_CIRCLE_GREEN }
                .flatMap { it.dates }

            calendarView.addDecorator(
                this@DetailCalendarFragment.context?.let {
                    EventDecorator(
                        BG_CIRCLE_GREEN,
                        watered,
                        it
                    )
                }
            )
        }
    }
}