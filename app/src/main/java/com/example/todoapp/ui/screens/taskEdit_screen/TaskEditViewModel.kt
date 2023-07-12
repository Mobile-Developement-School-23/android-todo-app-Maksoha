package com.example.todoapp.ui.screens.taskEdit_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.ui.model.TaskEditAction
import com.example.todoapp.ui.model.TaskEditUiState
import com.example.todoapp.utils.toLocalDate
import com.example.todoapp.utils.toLong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class TaskEditViewModel @Inject constructor(private val repository: ToDoRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskEditUiState())
    val uiState = _uiState


    //    private val errorState: MutableStateFlow<Int> = MutableStateFlow(200)
    private val selectedItem: MutableStateFlow<ToDoItem?> = MutableStateFlow(null)


    fun onAction(action: TaskEditAction) {
        when (action) {
            is TaskEditAction.UpdateDeadline -> _uiState.update {
                uiState.value.copy(deadline = action.deadline?.toLocalDate())
            }

            is TaskEditAction.UpdateDescription -> _uiState.update {
                uiState.value.copy(description = action.description)
            }

            is TaskEditAction.UpdateImportance -> _uiState.update {
                uiState.value.copy(importance = action.importance)
            }

            TaskEditAction.DeleteTask -> selectedItem.value?.let { deleteItem() }
            TaskEditAction.SaveTask ->
                if (selectedItem.value == null) addItem()
                else updateItem()
        }
    }


    fun selectItem(id: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == null) {
                setDefaultValue()
            } else {
                val item = repository.getItemById(id)
                setItemValue(item)
            }
        }
    }

    fun getSelectedItem(): StateFlow<ToDoItem?> {
        return selectedItem
    }


    private fun addItem() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(
                ToDoItem(
                    id = UUID.randomUUID().toString(),
                    text = _uiState.value.description,
                    importance = _uiState.value.importance,
                    deadline = _uiState.value.deadline?.toLong(),
                    done = false,
                    color = null,
                    createdAt = System.currentTimeMillis(),
                    changedAt = System.currentTimeMillis(),
                    lastUpdatedBy = ""
                )
            )
        }
    }

    private fun deleteItem() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedItem.value?.id?.let { repository.deleteItem(it) }
        }
    }

    fun updateItem() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedItem.value?.let {
                repository.updateItem(
                    it.copy(
                        text = _uiState.value.description,
                        importance = uiState.value.importance,
                        deadline = uiState.value.deadline?.toLong(),
                        changedAt = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    private fun setDefaultValue() {
        _uiState.update {
            uiState.value.copy(
                description = "",
                importance = Importance.COMMON,
                deadline = null,
                isEditing = false
            )
        }
    }

    private fun setItemValue(item: ToDoItem) {
        selectedItem.value = item
        uiState.update {
            uiState.value.copy(
                description = item.text,
                importance = item.importance,
                deadline = item.deadline?.toLocalDate(),
                isEditing = true
            )
        }
    }

//    fun getErrorState(): StateFlow<Int> = errorState


}