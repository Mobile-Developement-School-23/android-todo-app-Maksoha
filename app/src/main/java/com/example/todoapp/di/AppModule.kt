package com.example.todoapp.di

import android.app.Application
import com.example.todoapp.ToDoListApplication
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AppModule {
    @Binds
    abstract fun provideApplication(application: ToDoListApplication): Application

}