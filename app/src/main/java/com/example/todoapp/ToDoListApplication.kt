package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.di.AppModule
import com.example.todoapp.di.DaggerAppComponent
import javax.inject.Inject

class ToDoListApplication : Application() {

    @Inject
    lateinit var toDoRepository: ToDoRepository

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)
    }
}