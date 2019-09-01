package com.nexters.android.pliary.notification

import android.app.AlarmManager
import android.os.Build
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import java.util.*


class PliaryJobService : JobService() {
    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }

    override fun onStartJob(job: JobParameters): Boolean {
        Log.d("NotificationJobService", "onStartJob")
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val calendar = Calendar.getInstance()
        //알람시간 calendar에 set해주기

        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE),
            23,
            12,
            0
        )

        //알람 예약
        //manager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, sender)

        /**
         * Intent 플래그
         *    FLAG_ONE_SHOT : 한번만 사용하고 다음에 이 PendingIntent가 불려지면 Fail을 함
         *    FLAG_NO_CREATE : PendingIntent를 생성하지 않음. PendingIntent가 실행중인것을 체크를 함
         *    FLAG_CANCEL_CURRENT : 실행중인 PendingIntent가 있다면 기존 인텐트를 취소하고 새로만듬
         *    FLAG_UPDATE_CURRENT : 실행중인 PendingIntent가 있다면  Extra Data만 교체함
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent) //10초뒤 알람
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent)
        }

        /**
         * AlarmType
         *    RTC_WAKEUP : 대기모드에서도 알람이 작동함을 의미함
         *    RTC : 대기모드에선 알람을 작동안함
         */

        return false // Answers the question: "Is there still work going on?"
    }

}