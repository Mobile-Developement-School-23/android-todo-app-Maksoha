package com.example.todoapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.ToDoItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow as StateFlow

class ToDoItemViewModel (private val repository: ToDoItemsRepository) : ViewModel() {
    private val selectedItem: MutableStateFlow<ToDoItem?> = MutableStateFlow(null)
    private val itemsSize : MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            repository.getItems().collect {
                itemsSize.value = it.size
            }
        }
    }
    fun selectItem(id : String?) {
        viewModelScope.launch {
            if (id == null) {
                selectedItem.value = null
            }
            else {
                selectedItem.value = repository.getItem(id)
                Log.d("check", id)
            }
        }
    }

    fun getSelectedItem() : StateFlow<ToDoItem?> {
        return selectedItem
    }


    fun addItem(request: ToDoItem) {
        viewModelScope.launch {
            repository.addItem(request)
        }
    }

    fun deleteItem(item: ToDoItem) {
        viewModelScope.launch {
            repository.deleteItem(item.id)
        }
    }

     fun updateItem(editItem: ToDoItem) {
         viewModelScope.launch {
             repository.updateItem(editItem)
         }
     }



}
class ToDoItemViewModelFactory(private val repository: ToDoItemsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

