package com.example.todoapp.data.repositories

import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class ToDoItemsRepository {
    private val items: MutableStateFlow<List<ToDoItem>> = MutableStateFlow(emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            loadData()
        }
    }

    private fun loadData() {
        addItem(ToDoItem(
            "item_1",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.HIGH,
            Date(1677084800000),
            false,
            Date(1672483200000),
            Date(1672483200000)
        ))
        addItem(ToDoItem(
            "item_2",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            Date(1677084800000),
            true,
            Date(1672483200000),
            Date(1672483200000)
        ))
        addItem(ToDoItem(
            "item_3",
            "Бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.LOW,
            Date(1677084800000),
            false,
            Date(1672483200000),
            Date(1672483200000)
        ))
        addItem(ToDoItem(
            "item_4",
            "Оооооооооооооооооооочееееееееенььььььььььььььь бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            null,
            false,
            Date(1672483200000),
            Date(1672483200000)
        ))
        addItem(ToDoItem(
            "item_5",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.HIGH,
            Date(1677084800000),
            true,
            Date(1672483200000),
            Date(1672483200000)
        ))
        addItem(ToDoItem(
            "item_6",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            Date(1677084800000),
            false,
            Date(1672483200000),
            Date(1672483200000)
        ))
        addItem(ToDoItem(
            "item_7",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.LOW,
            null,
            false,
            Date(1672483200000),
            Date(1672483200000)
        ))
        addItem(ToDoItem(
            "item_8",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            Date(1677084800000),
            true,
            Date(1672483200000),
            Date(1672483200000)
        ))
        addItem(ToDoItem(
            "item_9",
            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.HIGH,
            Date(1677084800000),
            false,
            Date(1672483200000),
            Date(1672483200000)
        ))
        addItem(ToDoItem(
            "item_10",
            "Оооооооооооооооооооочееееееееееееееееееееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
            Importance.COMMON,
            Date(1677084800000),
            true,
            Date(1672483200000),
            Date(1672483200000)
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
