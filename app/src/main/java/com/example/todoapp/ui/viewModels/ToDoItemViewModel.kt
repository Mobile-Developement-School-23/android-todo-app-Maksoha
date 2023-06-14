package com.example.todoapp.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.ToDoItemsRepository
import kotlinx.coroutines.launch

class ToDoItemViewModel (private val repository: ToDoItemsRepository) : ViewModel() {
    private val item: MutableLiveData<ToDoItem?> = MutableLiveData()


    fun getItem() : LiveData<ToDoItem?> {
        return item
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
        item.value = selectedItem
    }

    fun addItem(item: ToDoItem) {
        repository.addItem(item)

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

