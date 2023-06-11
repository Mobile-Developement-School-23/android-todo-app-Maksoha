package com.example.todoapp.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoListModel
import com.example.todoapp.data.repositories.ToDoListRepository
import kotlinx.coroutines.launch

class ToDoItemViewModel (private val repository: ToDoListRepository) : ViewModel() {
    private val item: MutableLiveData<ToDoListModel?> = MutableLiveData()


    fun getItem() : LiveData<ToDoListModel?> {
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


    fun setItem(selectedItem: ToDoListModel?) {
        item.value = selectedItem
    }

    fun addItem(item: ToDoListModel) {
        repository.addItem(item)

    }

    fun remove(item: ToDoListModel) {
        repository.removeItem(item)
    }

     fun updateItem(selectItem: ToDoListModel, newItem: ToDoListModel) {
        repository.updateItem(selectItem, newItem)
     }


}
class ToDoItemViewModelFactory(private val repository: ToDoListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

