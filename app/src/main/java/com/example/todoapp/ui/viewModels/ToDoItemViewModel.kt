package com.example.todoapp.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.ToDoItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class ToDoItemViewModel (private val repository: ToDoItemsRepository) : ViewModel() {
    private val selectedItem: MutableStateFlow<ToDoItem?> = MutableStateFlow(null)

    fun getSelectedItem() : StateFlow<ToDoItem?> {
        return selectedItem
    }

    fun getItemsSize() : Int {
        var itemSize = 0
        viewModelScope.launch {
            repository.getItems().collect { items->
                 itemSize = items.size
            }
        }
        return itemSize
    }


    fun setItem(selectedItem: ToDoItem?) {
        this.selectedItem.value = selectedItem
    }

    fun addItem(revision: Int, request: ToDoItem) {
        viewModelScope.launch {
            repository.addItem(revision, request)
        }
    }

    fun deleteItem(item: ToDoItem) {
        repository.deleteItem(item)
    }

     fun updateItem(selectItem: ToDoItem, newItem: ToDoItem) {
        repository.updateItem(selectItem, newItem)
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

