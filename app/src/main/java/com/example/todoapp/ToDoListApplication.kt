package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.data_sources.room.root.AppDatabase
import com.example.todoapp.data.repositories.LocalRepository
import com.example.todoapp.data.repositories.NetworkRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ToDoListApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy {AppDatabase.getDatabase(this, applicationScope)  }

    private val networkRepository by lazy {NetworkRepository()  }
    private val localRepository by lazy {LocalRepository(database.toDoItemDao())}

    val toDoRepository by lazy {ToDoRepositoryImpl(localRepository, networkRepository) }

}