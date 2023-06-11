package com.example.todoapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.ToDoListModel
import com.example.todoapp.data.repositories.ToDoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Optional.empty

class ToDoItemViewModel (private val repository: ToDoListRepository) : ViewModel() {
    private lateinit var item: MutableStateFlow<ToDoListModel>
    private val itemsSize: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        subscribeToItems()
    }


    private fun subscribeToItems() {
        viewModelScope.launch {
            repository.getItemsFlow().collect { items ->
                itemsSize.value = items.size
            }
        }
    }

    fun getItem() : ToDoListModel{
        return item.value
    }


    fun getItemsSize() : Flow<Int> {
        return itemsSize
    }


    fun setItem(selectedItem : ToDoListModel) {
        item = MutableStateFlow(selectedItem)
    }

    fun addItem(item: ToDoListModel) {
        repository.addItem(item)
    }

    fun updateItem(selectItem: ToDoListModel, newItem: ToDoListModel) {
        repository.updateItem(selectItem, newItem)
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

}