package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.data_sources.room.root.AppDatabase
import com.example.todoapp.data.repositories.LocalDataSource
import com.example.todoapp.data.repositories.RemoteDataSource
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ToDoListApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    private val networkRepository by lazy { RemoteDataSource() }
    private val localRepository by lazy { LocalDataSource(database.toDoItemDao()) }

    val toDoRepository by lazy { ToDoRepositoryImpl(localRepository, networkRepository) }
}