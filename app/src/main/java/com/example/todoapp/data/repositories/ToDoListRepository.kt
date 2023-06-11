package com.example.todoapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.models.ToDoListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ToDoListRepository {
    private val items: MutableStateFlow<List<ToDoListModel>> = MutableStateFlow(emptyList())

    init {
        loadData()
    }

    private fun loadData() {
        for (i in 1..10) {
            val id = "item_$i"
            val text = "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст"
            val importance = "Low"
            val deadline = null
            val isDone = false
            val creationDate = "2023-05-17"
            val changeDate = "2023-05-17"


            addItem(ToDoListModel(id, text, importance, deadline, isDone, creationDate, changeDate))
        }
    }
    fun addItem(newItem: ToDoListModel) {
        val currentList = items.value.toMutableList()
        currentList.add(newItem)
        items.value = currentList
    }


    fun getItems(): StateFlow<List<ToDoListModel>> {
        return items
    }

    fun removeItem(selectItem: ToDoListModel) {
        val currentList = items.value.toMutableList()
        currentList.removeIf { it == selectItem }
        items.value = currentList
    }

    fun updateItem(selectItem: ToDoListModel, newItem: ToDoListModel) {
        val currentList = items.value.toMutableList()
        val updatedList = currentList.map { if (it == selectItem) newItem else it }
        items.value = updatedList
    }

}
