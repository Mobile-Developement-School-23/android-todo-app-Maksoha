package com.example.todoapp.ui.viewModels


import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoListModel
import com.example.todoapp.data.repositories.ToDoListRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ToDoListViewModel(private val repository: ToDoListRepository) : ViewModel() {
    private var allItems: MutableLiveData<List<ToDoListModel>> = MutableLiveData()

    init {
        viewModelScope.launch {
            repository.getItems().collect { items ->
                allItems.value = items
            }
        }
    }



    fun getItems(): LiveData<List<ToDoListModel>> {
        return allItems
    }


    fun updateItem(selectItem: ToDoListModel, newItem: ToDoListModel) {
        repository.updateItem(selectItem, newItem)
    }


}

class ToDoListViewModelFactory(private val repository: ToDoListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
