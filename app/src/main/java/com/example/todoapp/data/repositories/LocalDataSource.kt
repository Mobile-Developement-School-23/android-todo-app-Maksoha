package com.example.todoapp.data.repositories

import com.example.todoapp.data.data_sources.room.dao.ToDoItemDao
import com.example.todoapp.data.data_sources.room.entities.ToDoItemEntity
import com.example.todoapp.data.models.ToDoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalDataSource(private val toDoItemDao: ToDoItemDao) {

    suspend fun clearDatabase() {
        toDoItemDao.deleteAllItems()
    }

    suspend fun getItems(): List<ToDoItem> = withContext(Dispatchers.IO) {
        toDoItemDao.getItems().map(ToDoItemEntity::toDomainModel)
    }

    fun getFlowItems(): Flow<List<ToDoItem>> {
        return toDoItemDao.getFlowItems().map { it.map(ToDoItemEntity::toDomainModel) }
    }

    fun getUndoneItems(): Flow<List<ToDoItem>> {
        return toDoItemDao.getUndoneItems().map { it.map(ToDoItemEntity::toDomainModel) }
    }

    fun getItemById(itemId: String): ToDoItem {
        return toDoItemDao.getItemById(itemId).toDomainModel()
    }

    fun getNumberOfItems(): Flow<Int> = toDoItemDao.getNumberOfItems()

    fun getNumberOfDoneItems(): Flow<Int> = toDoItemDao.getNumberOfDoneItems()

    suspend fun updateItems(newItems: List<ToDoItem>) {
        toDoItemDao.updateItems(newItems.map(ToDoItem::toEntity))
    }

    suspend fun addItem(newItem: ToDoItem) {
        toDoItemDao.addItem(newItem.toEntity())
    }

    suspend fun updateItem(updatedItem: ToDoItem) {
        toDoItemDao.updateItem(updatedItem.toEntity())
    }

    suspend fun deleteItem(itemId: String) {
        toDoItemDao.deleteItemById(itemId)
    }


}

