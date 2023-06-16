package com.example.todoapp.data.repositories

import androidx.compose.material3.TabPosition
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ToDoItemsRepository {
    private val items: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())

    init {
        loadData()
    }

    private fun loadData() {
        addItem(ToDoItem(
            "item_1",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.HIGH,
            "25 июня 2023",
            false,
            "21 июня 2023",
            "21 июня 2023"
        ))
        addItem(ToDoItem(
            "item_2",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            "26 июня 2023",
            true,
            "22 июня 2023",
            "22 июня 2023"
        ))
        addItem(ToDoItem(
            "item_3",
            "Бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.LOW,
            "27 июня 2023",
            false,
            "23 июня 2023",
            "23 июня 2023"
        ))
        addItem(ToDoItem(
            "item_4",
            "Оооооооооооооооооооочееееееееенььььььььььььььь бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            null,
            false,
            "24 июня 2023",
            "24 июня 2023"
        ))
        addItem(ToDoItem(
            "item_5",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.HIGH,
            "29 июня 2023",
            true,
            "25 июня 2023",
            "25 июня 2023"
        ))
        addItem(ToDoItem(
            "item_6",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            "30 июня 2023",
            false,
            "26 июня 2023",
            "26 июня 2023"
        ))
        addItem(ToDoItem(
            "item_7",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.LOW,
            null,
            false,
            "27 июня 2023",
            "27 июня 2023"
        ))
        addItem(ToDoItem(
            "item_8",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            "2 июля 2023",
            true,
            "28 июня 2023",
            "28 июня 2023"
        ))
        addItem(ToDoItem(
            "item_9",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.HIGH,
            "3 июля 2023",
            false,
            "29 июня 2023",
            "29 июня 2023"
        ))
        addItem(ToDoItem(
            "item_10",
            "Оооооооооооооооооооочееееееееееееееееееееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            "4 июля 2023",
            true,
            "30 июня 2023",
            "30 июня 2023"
        ))



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

    fun moveItem(fromIndex : Int, toIndex : Int) {
            val itemList = items.value.toMutableList()
            val item = itemList.removeAt(fromIndex)
            itemList.add(toIndex, item)
            items.value = itemList

    }


}
