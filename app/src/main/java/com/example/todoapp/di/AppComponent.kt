package com.example.todoapp.di

import android.content.Context
import com.example.todoapp.MainActivity
import com.example.todoapp.ToDoListApplication
import com.example.todoapp.ui.view.ItemFragment
import com.example.todoapp.ui.view.ListFragment
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
        fun create(@BindsInstance application: ToDoListApplication): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(itemFragment: ItemFragment)
    fun inject(listFragment: ListFragment)

}