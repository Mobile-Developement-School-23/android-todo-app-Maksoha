package com.example.todoapp.data.models

import java.util.Date

data class ToDoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Date? = null,
    var isDone: Boolean = false,
    val creationDate: Date,
    val changeDate: Date?
)
enum class Importance {
    LOW,
    COMMON,
    HIGH
}
