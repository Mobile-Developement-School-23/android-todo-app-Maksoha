package com.example.todoapp.data.models

data class ToDoItemResponse(
    val status: String,
    val element: ToDoItem,
    val revision: Int
)
