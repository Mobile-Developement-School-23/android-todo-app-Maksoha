package com.example.todoapp.data.repositories

import android.util.Log
import com.example.todoapp.data.models.ToDoListModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class ToDoListRepository {
    private val items: MutableStateFlow<List<ToDoListModel>> = MutableStateFlow(emptyList())

    init {
        for (i in 1..10) {
            val id = "item_$i"
            val text = "Оооооооооооооооооооочееееееееень бооооооольшоооооой тексссссссссссссссссссссст"
            val importance = "Low"
            val deadline = null
            val isDone = false
            val creationDate = "2023-05-17"
            val changeDate = "2023-05-17"
            addItem(ToDoListModel(id, text, importance, deadline, isDone, creationDate, changeDate))
        }
    }
    fun addItem(item: ToDoListModel) {
        val currentItems = items.value.toMutableList()
        currentItems.add(item)
        items.value = currentItems
    }

    fun getItemsFlow(): Flow<List<ToDoListModel>> {
        return items
    }

    fun removeItem(selectItem: ToDoListModel) {
        val currentItems = items.value.toMutableList()
        currentItems.remove(selectItem)
        items.value = currentItems
    }

    fun updateItem(selectItem: ToDoListModel, newItem: ToDoListModel) {
        val currentItems = items.value.toMutableList()
        val itemIndex = currentItems.indexOfFirst { it == selectItem }
        if (itemIndex != -1) {
            currentItems[itemIndex] = newItem
            items.value = currentItems
        }
    }

}
