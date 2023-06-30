package com.example.todoapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.LocalRepository
import com.example.todoapp.data.repositories.NetworkRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.StateFlow as StateFlow

class ToDoItemViewModel (private val repository: ToDoRepositoryImpl) : ViewModel() {
    private val selectedItem: MutableStateFlow<ToDoItem?> = MutableStateFlow(null)
    fun selectItem(id: String?) {
        viewModelScope.launch(Dispatchers.IO){
            if (id == null) {
                selectedItem.value = null
            } else {
                selectedItem.value = repository.getItemById(id)
            }
        }
    }

    fun getSelectedItem() : StateFlow<ToDoItem?> {
        return selectedItem
    }


    fun addItem(item: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }

    fun deleteItem(item: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(item.id)
        }
    }

     fun updateItem(editItem: ToDoItem) {
         viewModelScope.launch(Dispatchers.IO) {
             repository.updateItem(editItem)
         }
     }



}
class ToDoItemViewModelFactory(private val repository: ToDoRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

