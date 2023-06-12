package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.repositories.ToDoItemsRepository

class ToDoListApplication : Application() {
    val repository = ToDoItemsRepository()
}