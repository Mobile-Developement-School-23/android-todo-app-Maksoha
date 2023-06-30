package com.example.todoapp.ui.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.LocalRepository
import com.example.todoapp.data.repositories.NetworkRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


class ToDoListViewModel(private val repository: ToDoRepositoryImpl) : ViewModel() {
    private var visibility : MutableStateFlow<Boolean> = MutableStateFlow(true)

    private val _items: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())
    val items: StateFlow<List<ToDoItem>> = _items

    private val _undoneItems: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())
    val undoneItems: StateFlow<List<ToDoItem>> = _undoneItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            combine(repository.getItems(), repository.getUndoneItems()) { items, undoneItems ->
                Pair(items, undoneItems)
            }.collect { (items, undoneItems) ->
                _items.value = items
                _undoneItems.value = undoneItems
            }
        }
    }

    suspend fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.refreshData()
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
