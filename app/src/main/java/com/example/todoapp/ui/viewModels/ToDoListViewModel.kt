package com.example.todoapp.ui.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.ToDoItemsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ToDoListViewModel(private val repository: ToDoItemsRepository) : ViewModel() {
    private val _items: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())
    val items: StateFlow<List<ToDoItem>> = _items
    private val _unCompletedItems: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())
    val unCompletedItems: StateFlow<List<ToDoItem>> = _unCompletedItems
    private var visibility : MutableStateFlow<Boolean> = MutableStateFlow(true)

    init {
        refreshItems()
    }
    fun refreshItems() {
        viewModelScope.launch {
            delay(10)
            repository.getItems().collect { items ->
                _items.value = items
                _unCompletedItems.value = items.filter {!it.done}
            }
        }
    }


    fun deleteItem(item: ToDoItem) {
        viewModelScope.launch {
            repository.deleteItem(item.id)
        }
    }

    fun deleteItemByPosition (position: Int) {
        viewModelScope.launch {
            repository.deleteItem(_items.value[position].id)
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
class ToDoListViewModelFactory(private val repository: ToDoItemsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
