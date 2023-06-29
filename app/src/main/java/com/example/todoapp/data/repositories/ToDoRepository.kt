package com.example.todoapp.data.repositories

import com.example.todoapp.data.models.ToDoItem
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getItems(): Flow<List<ToDoItem>>

    suspend fun refreshData()

    fun getUndoneItems(): Flow<List<ToDoItem>>

    fun getItemById(itemId: String): ToDoItem

    suspend fun updateItems()

    suspend fun addItem(newItem : ToDoItem)

    suspend fun updateItem(updatedItem: ToDoItem)

    suspend fun deleteItem(itemId: String)

}
