package com.example.todoapp.ui.model

sealed class TaskEditEvent {
    object NavBack : TaskEditEvent()
    object SaveTask : TaskEditEvent()
}