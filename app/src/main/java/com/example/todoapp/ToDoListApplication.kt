package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.repositories.ToDoListRepository

class ToDoListApplication : Application() {
    val repository = ToDoListRepository()
}