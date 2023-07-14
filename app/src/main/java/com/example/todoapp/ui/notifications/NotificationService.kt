package com.example.todoapp.ui.notifications


import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

const val REMINDER_NOTIFICATION_REQUEST_CODE = 1
const val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
const val NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME"

class NotificationService @Inject constructor(
    private val context: Context
) {
    fun startService(
        time: String = "00:00",
        id: Int = REMINDER_NOTIFICATION_REQUEST_CODE
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val (hours, min) = time.split(":").map { it.toInt() }
        val intent = Intent(
            context.applicationContext,
            NotificationReceiver::class.java
        ).let { intent ->
            PendingIntent.getBroadcast(
                context.applicationContext,
                id,
                intent,
                PendingIntent.FLAG_MUTABLE
            )
        }

        val calendar: Calendar = Calendar.getInstance(Locale.getDefault()).apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, min)
        }

        if (Calendar.getInstance(Locale.getDefault())
                .apply { add(Calendar.MINUTE, 1) }.timeInMillis - calendar.timeInMillis > 0
        ) {
            calendar.add(Calendar.DATE, 1)
        }

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, intent),
            intent
        )
    }

//    fun stopService(
//        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE
//    ) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, NotificationReceiver::class.java).let { intent ->
//            PendingIntent.getBroadcast(
//                context,
//                reminderId,
//                intent,
//                PendingIntent.FLAG_MUTABLE
//            )
//        }
//        alarmManager.cancel(intent)
//    }
}