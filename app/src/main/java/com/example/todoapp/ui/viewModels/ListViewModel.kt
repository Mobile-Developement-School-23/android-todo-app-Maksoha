package com.example.todoapp.ui.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.repositories.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


class ListViewModel @Inject constructor(private val repository: ToDoRepository) : ViewModel() {
    private val _visibility: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val visibility: StateFlow<Boolean> = _visibility

    private val _itemsSize: MutableStateFlow<Int> = MutableStateFlow(0)
    val itemsSize: StateFlow<Int> = _itemsSize

    private val _doneItemsSize: MutableStateFlow<Int> = MutableStateFlow(0)
    val doneItemsSize: StateFlow<Int> = _doneItemsSize

    private val _items: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())
    val items: StateFlow<List<ToDoItem>> = _items

    private val errorState : MutableStateFlow<Int> = MutableStateFlow(200)

    init {
        observeItems()
        observeItemsSize()
        observeDoneItemsSize()
    }

    private fun observeItems() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(repository.getItems(), repository.getUndoneItems(), visibility) { items, undoneItems, isVisible ->
                if (isVisible) {
                    items
                } else {
                    undoneItems
                }
            }.collect { combinedItems ->
                _items.value = combinedItems
            }
        }
    }

    private fun observeItemsSize() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNumberOfItems().collect {
                _itemsSize.value = it
            }
        }
    }

    private fun observeDoneItemsSize() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNumberOfDoneItems().collect {
                _doneItemsSize.value = it
            }
        }
    }

    suspend fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            errorState.value = repository.refreshData()
        }
    }

    fun deleteItem(item: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            errorState.value = repository.deleteItem(item.id)
        }
    }

    fun updateItem(editItem: ToDoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            errorState.value = repository.updateItem(editItem)
        }
    }

    fun getErrorState() : StateFlow<Int> = errorState

    fun changeStateVisibility() {
        _visibility.value = !_visibility.value
    }
}

