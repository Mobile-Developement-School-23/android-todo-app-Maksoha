package com.example.todoapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ToDoRepository) : ViewModel() {
    private val selectedItem: MutableStateFlow<ToDoItem?> = MutableStateFlow(null)
    private val errorState: MutableStateFlow<Int> = MutableStateFlow(200)
    fun selectItem(id: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == null) {
                selectedItem.value = null
            } else {
                selectedItem.value = repository.getItemById(id)
            }
        }
    }

    fun getSelectedItem(): StateFlow<ToDoItem?> {
        return selectedItem
    }


    fun addItem(item: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            errorState.value = repository.addItem(item)
        }
    }

    fun deleteItem(item: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            errorState.value = repository.deleteItem(item.id)
        }
    }

    fun updateItem(editItem: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            errorState.value = repository.updateItem(editItem)
        }
    }

    fun getErrorState(): StateFlow<Int> = errorState

}


