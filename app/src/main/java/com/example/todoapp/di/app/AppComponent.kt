package com.example.todoapp.di.app

import android.content.Context
import com.example.todoapp.di.AppScope
import com.example.todoapp.di.activity.ActivityComponent
import dagger.BindsInstance
import dagger.Component


@AppScope
@Component(modules = [DatabaseModule::class, NetworkModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun activityComponent(): ActivityComponent.Factory

}