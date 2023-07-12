package com.example.todoapp.di.app

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.data_sources.room.dao.ToDoItemDao
import com.example.todoapp.data.data_sources.room.root.AppDatabase
import com.example.todoapp.di.AppScope
import dagger.Module
import dagger.Provides

@Module
interface DatabaseModule {

    @AppScope
    @Provides
    fun provideToDoItemDao(database: AppDatabase): ToDoItemDao {
        return database.toDoItemDao()
    }

    @AppScope
    @Provides
    fun provideDatabase(context: Context): AppDatabase {

        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}
