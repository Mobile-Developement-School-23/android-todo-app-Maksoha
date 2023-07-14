package com.example.todoapp.di.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.data_sources.local.room.dao.ToDoItemDao
import com.example.todoapp.data.data_sources.local.room.root.AppDatabase
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @AppScope
    @Provides
    fun provideToDoItemDao(database: AppDatabase): ToDoItemDao {
        return database.toDoItemDao()
    }

    @AppScope
    @Provides
    fun provideDatabase(application: Application): AppDatabase {

        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}
