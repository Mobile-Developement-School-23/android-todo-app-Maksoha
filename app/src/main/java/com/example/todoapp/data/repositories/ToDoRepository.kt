package com.example.todoapp.data.repositories

import com.example.todoapp.data.models.ToDoItem
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getItems(): Flow<List<ToDoItem>>

    suspend fun refreshData(): Int

    fun getUndoneItems(): Flow<List<ToDoItem>>

    fun getItemById(itemId: String): ToDoItem

    suspend fun updateItems(): Int

    suspend fun addItem(newItem: ToDoItem): Int

    suspend fun updateItem(updatedItem: ToDoItem): Int

    suspend fun deleteItem(itemId: String): Int

    suspend fun getNumberOfItems(): Flow<Int>
    suspend fun getNumberOfDoneItems(): Flow<Int>

    fun getDeadlineItems(time : Long) : List<ToDoItem>


}
