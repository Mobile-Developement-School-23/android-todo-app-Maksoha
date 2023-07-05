package com.example.todoapp.di

import android.app.Application
import com.example.todoapp.data.data_sources.room.dao.ToDoItemDao
import com.example.todoapp.data.data_sources.room.root.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    @Provides
    fun provideToDoItemDao(database: AppDatabase): ToDoItemDao {
        return database.toDoItemDao()
    }

}
