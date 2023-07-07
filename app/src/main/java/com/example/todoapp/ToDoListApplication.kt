package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.data_sources.networks.DataRefreshWorker
import com.example.todoapp.di.app.AppComponent
import com.example.todoapp.di.app.DaggerAppComponent

class ToDoListApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .factory()
            .create(this)

        DataRefreshWorker.startRefresh(this)

    }


}