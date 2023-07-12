package com.example.todoapp.ui.model

import com.example.todoapp.data.models.Importance
import com.example.todoapp.utils.Constants.Companion.todayLocalDate
import java.time.LocalDate

data class TaskEditViewState (
    val description : String = "",
    val importance : Importance = Importance.COMMON,
    val deadline : LocalDate = todayLocalDate,
    val isDeadlineVisibile : Boolean = false,
    val isEditing : Boolean = false
)