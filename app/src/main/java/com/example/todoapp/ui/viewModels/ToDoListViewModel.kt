package com.example.todoapp.ui.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoListModel
import com.example.todoapp.data.repositories.ToDoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ToDoListViewModel(private val repository: ToDoListRepository) : ViewModel() {
    private val itemsFlow: MutableStateFlow<List<ToDoListModel>> = MutableStateFlow(emptyList())

    init {
        subscribeToItems()
    }

    private fun subscribeToItems() {
        viewModelScope.launch {
            repository.getItemsFlow().collect { items ->
                itemsFlow.value = items
            }
        }
    }

    fun getItemsFlow(): Flow<List<ToDoListModel>> {
        return itemsFlow
    }

    fun addItem(item: ToDoListModel) {
        repository.addItem(item)
    }


    fun updateItem(selectItem: ToDoListModel, isDone: Boolean) {
        repository.updateItem(selectItem, isDone)
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
