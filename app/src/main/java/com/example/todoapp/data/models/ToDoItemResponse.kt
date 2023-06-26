package com.example.todoapp.data.models

import com.google.gson.annotations.SerializedName

data class ToDoItemResponse(
    val status: String,
    val element: ToDoItem,
    val revision: Int
)
