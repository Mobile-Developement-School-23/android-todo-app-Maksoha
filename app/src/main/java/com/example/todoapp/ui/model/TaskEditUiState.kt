package com.example.todoapp.ui.model

import com.example.todoapp.data.models.Importance
import java.time.LocalDate

data class TaskEditUiState (
    val description : String = "",
    val importance : Importance = Importance.COMMON,
    val deadline : LocalDate? = null,
    val isEditing : Boolean = false
)