package com.example.todoapp.di.app

import android.app.Application
import android.content.Context
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.di.activity.ActivityComponent
import com.example.todoapp.ui.notifications.NotificationService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Scope
annotation class AppScope

@AppScope
@Component(modules = [AppModule::class, DatabaseModule::class, NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun activityComponent(): ActivityComponent.Factory

    fun notificationService() : NotificationService

    fun repository() : ToDoRepository

}