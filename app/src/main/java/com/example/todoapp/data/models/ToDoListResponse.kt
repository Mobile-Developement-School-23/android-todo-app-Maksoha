package com.example.todoapp.data.models

data class ToDoListResponse(
    val status: String,
    val list: List<ToDoItem>,
    val revision: Int
)