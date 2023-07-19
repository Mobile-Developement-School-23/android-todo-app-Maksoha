package com.example.todoapp.data.data_sources.local.room.root

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.data_sources.local.room.dao.ToDoItemDao
import com.example.todoapp.data.data_sources.local.room.entities.ToDoItemEntity


@Database(entities = [ToDoItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun toDoItemDao(): ToDoItemDao

}