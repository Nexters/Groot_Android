package com.nexters.android.pliary.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FLAG_AUTO_CANCEL
import com.nexters.android.pliary.R
import com.nexters.android.pliary.view.main.MainActivity


class AlarmBroadcastReceiver : BroadcastReceiver() {
    private val ID = 222

    companion object{
        const val NOTIFICATION_ID = "NOTIFICATION_ID"
        const val NOTIFICATION_TITLE = "NOTIFICATION_TITLE"
        const val NOTIFICATION_CONTENT = "NOTIFICATION_CONTENT"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmBroadcastReceiver", "얍얍얍얍얍얍얍얍얍얍 onReceive")

        intent?.let {
            val id = it.getStringExtra(NOTIFICATION_ID)
            val title = it.getStringExtra(NOTIFICATION_TITLE)
            val content = it.getStringExtra(NOTIFICATION_CONTENT)

            context?.let {

                Log.d("AlarmBroadcastReceiver", "얍얍얍얍얍얍얍얍얍얍 Making NotificationCompat : $content")
                val builder =
                    NotificationCompat.Builder(context, id ?: context.toString())
                        .setSmallIcon(R.mipmap.pliary_icon)
                        .setContentTitle(title)  //알람 제목
                        .setContentText(content) //알람 내용
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT) //알람 중요도

                val notificationIntent = Intent(context, MainActivity::class.java)
                notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

                val pendingIntentActivity = PendingIntent.getActivity(context, 0, notificationIntent, 0)
                builder.setContentIntent(pendingIntentActivity)

                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(ID, builder.build().apply { flags = FLAG_AUTO_CANCEL }) //알람 생성
            }
        }


    }
}