package com.example.todoapp

import android.app.Application
import com.example.todoapp.di.AppComponent
import com.example.todoapp.di.DaggerAppComponent

class ToDoListApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .factory()
            .create(this)
    }
}