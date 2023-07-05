package com.example.todoapp.di

import android.app.Application
import com.example.todoapp.ToDoListApplication
import dagger.Module
import dagger.Provides


@Module
class AppModule(private val application: ToDoListApplication) {

    @Provides
    fun provideApplication() : Application = application



}