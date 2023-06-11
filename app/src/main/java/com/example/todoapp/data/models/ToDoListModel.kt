package com.example.todoapp.data.models

data class ToDoListModel(val id : String, val text : String, val importance : String,
                         val deadline : String? = null, var isDone : Boolean = false, val creation_date : String,
                         val change_date : String)