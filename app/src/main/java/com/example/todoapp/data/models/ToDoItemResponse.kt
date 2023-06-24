package com.example.todoapp.data.models

data class ToDoItemResponse(
    val status: String,
    val item: ToDoItem,
    val revision: Int
)
