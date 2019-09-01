package com.nexters.android.pliary.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import com.nexters.android.pliary.R


class AlarmBroadcastReceiver : BroadcastReceiver() {
    private val NOTICATION_ID = 222

    companion object{
        const val NOTIFICATION_ID = "NOTIFICATION_ID"
        const val NOTIFICATION_TITLE = "NOTIFICATION_TITLE"
        const val NOTIFICATION_CONTENT = "NOTIFICATION_CONTENT"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmBroadcastReceiver", "onReceive")

        intent?.let {
            val id = it.getStringExtra(NOTIFICATION_ID)
            val title = it.getStringExtra(NOTIFICATION_TITLE)
            val content = it.getStringExtra(NOTIFICATION_CONTENT)

            context?.let {
                val builder =
                    NotificationCompat.Builder(context, id ?: context.toString())
                        .setSmallIcon(R.drawable.pliary_icon_foreground)
                        .setContentTitle(title)  //알람 제목
                        .setContentText(content) //알람 내용
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT) //알람 중요도

                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(NOTICATION_ID, builder.build()) //알람 생성
            }
        }


    }
}