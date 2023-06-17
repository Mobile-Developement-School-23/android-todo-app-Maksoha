package com.example.todoapp.data.models

data class ToDoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: String? = null,
    var isDone: Boolean = false,
    val creationDate: String,
    val changeDate: String?
)
enum class Importance {
    LOW,
    COMMON,
    HIGH
}
