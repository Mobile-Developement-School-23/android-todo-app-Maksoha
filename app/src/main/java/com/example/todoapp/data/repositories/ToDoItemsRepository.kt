package com.example.todoapp.data.repositories

import androidx.compose.material3.TabPosition
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.todoapp.data.models.ToDoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ToDoItemsRepository {
    private val items: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())

    init {
        loadData()
    }

    private fun loadData() {
        for (i in 1..10) {
            val id = "item_$i"
            val text = "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст"
            val importance = "Срочная"
            val deadline = "25 июня 2023"
            val isDone = false
            val creationDate = "21 июня 2023"
            val changeDate = "21 июня 2023"
            addItem(ToDoItem(id, text, importance, deadline, isDone, creationDate, changeDate))
        }
    }
    fun addItem(newItem: ToDoItem) {
        val currentList = items.value.toMutableList()
        currentList.add(newItem)
        items.value = currentList
    }


    fun getItems(): StateFlow<List<ToDoItem>> {
        return items
    }

    fun deleteItem(selectItem: ToDoItem) {
        val currentList = items.value.toMutableList()
        currentList.removeIf { it == selectItem }
        items.value = currentList
    }

    fun deleteItemByPosition(position: Int) {
        val currentItems = items.value.toMutableList()
        if (position in 0 until currentItems.size) {
            currentItems.removeAt(position)
            items.value = currentItems
        }
    }

    fun updateItem(selectItem: ToDoItem, newItem: ToDoItem) {
        val currentList = items.value.toMutableList()
        val updatedList = currentList.map { if (it == selectItem) newItem else it }
        items.value = updatedList
    }


}
