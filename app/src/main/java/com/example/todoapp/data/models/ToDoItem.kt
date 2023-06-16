package com.example.todoapp.data.models

enum class Importance {
    LOW,
    COMMON,
    HIGH
}

data class ToDoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: String? = null,
    var isDone: Boolean = false,
    val creationDate: String,
    val changeDate: String?
)
