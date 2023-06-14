package com.example.todoapp.ui.viewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.ToDoItemsRepository
import kotlinx.coroutines.launch


class ToDoListViewModel(private val repository: ToDoItemsRepository) : ViewModel() {
    private var allItems: MutableLiveData<List<ToDoItem>> = MutableLiveData()

    init {
        viewModelScope.launch {
            repository.getItems().collect { items ->
                allItems.value = items
            }
        }
    }

    fun deleteItem(item: ToDoItem) {
        repository.removeItem(item)
    }

    fun getItems(): LiveData<List<ToDoItem>> {
        return allItems
    }


    fun updateItem(selectItem: ToDoItem, newItem: ToDoItem) {
        repository.updateItem(selectItem, newItem)
    }


}

class ToDoListViewModelFactory(private val repository: ToDoItemsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
