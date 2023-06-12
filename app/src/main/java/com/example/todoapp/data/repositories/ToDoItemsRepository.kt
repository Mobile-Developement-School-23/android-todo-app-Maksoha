package com.example.todoapp.data.repositories

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
            val importance = "Обычная"
            val deadline = null
            val isDone = false
            val creationDate = "2023-05-17"
            val changeDate = "2023-05-17"


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

    fun removeItem(selectItem: ToDoItem) {
        val currentList = items.value.toMutableList()
        currentList.removeIf { it == selectItem }
        items.value = currentList
    }

    fun updateItem(selectItem: ToDoItem, newItem: ToDoItem) {
        val currentList = items.value.toMutableList()
        val updatedList = currentList.map { if (it == selectItem) newItem else it }
        items.value = updatedList
    }

}
