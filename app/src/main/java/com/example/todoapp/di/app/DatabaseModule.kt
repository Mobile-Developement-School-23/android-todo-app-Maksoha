package com.example.todoapp.di.app

import android.content.Context
import com.example.todoapp.data.data_sources.room.dao.ToDoItemDao
import com.example.todoapp.data.data_sources.room.root.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideToDoItemDao(database: AppDatabase): ToDoItemDao {
        return database.toDoItemDao()
    }

}
