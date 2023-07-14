package com.example.todoapp.ui.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.todoapp.R
import com.example.todoapp.ToDoListApplication
import com.example.todoapp.data.models.toString
import com.example.todoapp.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendReminderNotification(
            context = context,
            channelId = NOTIFICATION_CHANNEL_ID
        )

        val app = context.applicationContext as ToDoListApplication
        app.appComponent.notificationService().startService()

    }
}

fun NotificationManager.sendReminderNotification(
    context: Context,
    channelId: String,
) {
    val applicationContext = context.applicationContext
    val app = applicationContext as ToDoListApplication
    val repository = app.appComponent.repository()

    val today = ZonedDateTime.now(ZoneId.systemDefault())
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0)
        .toInstant()
        .toEpochMilli()

    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        contentIntent,
        PendingIntent.FLAG_MUTABLE
    )

    CoroutineScope(Dispatchers.IO).launch {
        val deadLineItems = repository.getDeadlineItems(today + 10800000)
        withContext(Dispatchers.Main) {
            Log.d("check", deadLineItems.size.toString())
            deadLineItems.forEach { item ->
                val builder = NotificationCompat.Builder(applicationContext, channelId)
                    .setContentTitle("Дедлайн выполнения задачи")
                    .setContentText(
                        "Важность: ${item.importance.toString(context)}\n" + "Дело: ${item.text}}"
                    )
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                notify(item.id.hashCode(), builder.build())
            }
        }
    }


}