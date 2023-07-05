package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.di.AppComponent
import com.example.todoapp.di.AppModule
import com.example.todoapp.di.DaggerAppComponent
import com.example.todoapp.di.DatabaseModule
import com.example.todoapp.di.MainActivityModule
import com.example.todoapp.di.NetworkModule
import com.example.todoapp.di.ToDoRepositoryModule
import com.example.todoapp.di.ViewModelModule
import com.example.todoapp.di.WorkerModule
import javax.inject.Inject

class ToDoListApplication : Application() {

//    @Inject
//    lateinit var toDoRepository: ToDoRepository

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .mainActivityModule(MainActivityModule())
            .networkModule(NetworkModule())
            .toDoRepositoryModule(ToDoRepositoryModule())
            .viewModelModule(ViewModelModule())
            .workerModule(WorkerModule())
            .build()

        appComponent.inject(this)
    }
}