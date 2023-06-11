package com.example.todoapp.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.models.ToDoListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import java.util.Optional.empty

class ToDoItemViewModel : ViewModel() {
    private lateinit var item: MutableStateFlow<ToDoListModel>

    fun getItem() : ToDoListModel{
        return item.value
    }

    fun setItem(selectedItem : ToDoListModel) {
        item = MutableStateFlow(selectedItem)
    }
}