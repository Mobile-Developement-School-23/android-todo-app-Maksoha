package com.example.todoapp.ui.screens.taskEdit_screen

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.ui.model.TaskEditAction
import com.example.todoapp.ui.model.TaskEditEvent
import com.example.todoapp.ui.model.TaskEditViewState
import com.example.todoapp.utils.toLocalDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class TaskEditViewModel @Inject constructor(private val repository: ToDoRepository) : ViewModel() {
    private var isEditing = false

    private val _viewState = MutableStateFlow(TaskEditViewState())
    val viewState = _viewState

    private val _viewEvent = Channel<TaskEditEvent>()
    val viewEvent: Flow<TaskEditEvent> = _viewEvent.receiveAsFlow()

    fun onAction(action : TaskEditAction) {
        when (action) {
            is TaskEditAction.UpdateDeadline -> _viewState.update {
                viewState.value.copy(deadline = action.deadline.toLocalDate())
            }
            is TaskEditAction.UpdateDeadlineVisibility -> _viewState.update {
                viewState.value.copy(isDeadlineVisibile = action.visible)
            }
            is TaskEditAction.UpdateDescription -> TODO()
            is TaskEditAction.UpdateImportance -> TODO()
            TaskEditAction.DeleteTask -> TODO()
            TaskEditAction.Navigate -> TODO()
            TaskEditAction.SaveTask -> TODO()

        }
    }
}