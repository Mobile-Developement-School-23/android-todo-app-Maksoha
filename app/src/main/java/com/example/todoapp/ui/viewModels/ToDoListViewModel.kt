package com.example.todoapp.ui.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.LocalRepository
import com.example.todoapp.data.repositories.NetworkRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ToDoListViewModel(private val repository: ToDoRepositoryImpl) : ViewModel() {
    private var visibility : MutableStateFlow<Boolean> = MutableStateFlow(true)

    fun getItems() : Flow<List<ToDoItem>> = repository.getItems()

    suspend fun refreshData() {
        viewModelScope.launch {
            repository.refreshData()
        }
    }

    fun getUndoneItems() : Flow<List<ToDoItem>> = repository.getUndoneItems()

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

    fun changeStateVisibility() {
        visibility.value = !visibility.value
    }

    fun getStateVisibility() : StateFlow<Boolean> {
        return visibility
    }






}
class ToDoListViewModelFactory(private val repository: ToDoRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
