package com.nexters.android.pliary.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobScheduler
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.nexters.android.pliary.view.main.MainActivity
import java.util.*
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag
import com.firebase.jobdispatcher.*
import kotlinx.coroutines.Job


object JobSchedulerStart {
    private val JOB_ID = 1111

    fun start(context: Context) {
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
        val myJob = dispatcher.newJobBuilder()
            .setService(PliaryJobService::class.java) // 잡서비스 등록
            .setTag("TSLetterNotification")        // 태그 등록
            .setRecurring(true) //재활용
            .setLifetime(Lifetime.FOREVER) //다시켜도 작동을 시킬껀지?
            .setTrigger(Trigger.executionWindow(0, 60)) //트리거 시간
            .setReplaceCurrent(true)
            .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
            .build()
        dispatcher.mustSchedule(myJob)
    }
}