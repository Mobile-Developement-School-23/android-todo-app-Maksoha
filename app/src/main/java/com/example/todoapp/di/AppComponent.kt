package com.example.todoapp.di

import com.example.todoapp.ToDoListApplication
import com.example.todoapp.data.data_sources.networks.ToDoApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {
    fun inject(application: ToDoListApplication)
    fun inject() : ToDoApi
}