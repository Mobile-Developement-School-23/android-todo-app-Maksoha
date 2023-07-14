package com.example.todoapp.di.app

import android.app.Application
import com.example.todoapp.di.activity.ActivityComponent
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
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun activityComponent(): ActivityComponent.Factory

}