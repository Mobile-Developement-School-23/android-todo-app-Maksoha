package com.example.todoapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.todoapp.data.data_sources.networks.DataRefreshWorker
import com.example.todoapp.di.app.AppComponent
import com.example.todoapp.di.app.DaggerAppComponent
import com.example.todoapp.ui.notifications.NOTIFICATION_CHANNEL_ID
import com.example.todoapp.ui.notifications.NOTIFICATION_CHANNEL_NAME
import com.example.todoapp.ui.notifications.NotificationService

class ToDoListApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .factory()
            .create(this)

        DataRefreshWorker.startRefresh(this)

        createNotificationChannel()
        appComponent.notificationService().startService()

    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Used for notify task deadline"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}