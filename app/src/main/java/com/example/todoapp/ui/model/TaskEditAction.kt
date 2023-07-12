package com.example.todoapp.ui.model

import com.example.todoapp.data.models.Importance

sealed class TaskEditAction {
    data class UpdateDescription(val description : String) : TaskEditAction()
    data class UpdateImportance(val importance: Importance) : TaskEditAction()
    data class UpdateDeadline(val deadline: Long) : TaskEditAction()
    data class UpdateDeadlineVisibility(val visible : Boolean) : TaskEditAction()

    object SaveTask : TaskEditAction()
    object DeleteTask : TaskEditAction()
    object Navigate : TaskEditAction()

}