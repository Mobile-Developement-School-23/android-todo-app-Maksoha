package com.example.todoapp.di

import com.example.todoapp.MainActivity
import com.example.todoapp.ToDoListApplication
import com.example.todoapp.ui.view.ItemFragment
import com.example.todoapp.ui.view.ListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class,
    ToDoRepositoryModule::class, ViewModelModule::class, MainActivityModule::class,
WorkerModule::class])

interface AppComponent {
    fun inject(application: ToDoListApplication)
    fun inject(activity: MainActivity)

    fun inject(itemFragment: ItemFragment)

    fun inject(listFragment: ListFragment)


}