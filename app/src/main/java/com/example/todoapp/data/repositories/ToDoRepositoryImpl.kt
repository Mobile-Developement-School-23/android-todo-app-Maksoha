package com.example.todoapp.data.repositories

import com.example.todoapp.data.models.ToDoItem
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(
    private val localRepository: LocalRepository,
    private val networkRepository: NetworkRepository
) : ToDoRepository {

    override fun getItems(): Flow<List<ToDoItem>> = localRepository.getFlowItems()

    override suspend fun refreshData() {
//        localRepository.clearDatabase()
        val newItems = networkRepository.getItems()
        localRepository.updateItems(newItems)
    }

    override fun getUndoneItems(): Flow<List<ToDoItem>> = localRepository.getUndoneItems()

    override fun getItemById(itemId: String): ToDoItem = localRepository.getItemById(itemId)


    override suspend fun updateItems() {
        val updateItems = localRepository.getItems()
        networkRepository.updateItems(updateItems)
    }

    override suspend fun addItem(newItem: ToDoItem) {
        localRepository.addItem(newItem)
        networkRepository.addItem(newItem)
    }

    override suspend fun updateItem(updatedItem: ToDoItem) {
        localRepository.updateItem(updatedItem)
        networkRepository.updateItem(updatedItem)
    }

    override suspend fun deleteItem(itemId: String) {
        localRepository.deleteItem(itemId)
        networkRepository.deleteItem(itemId)
    }



}


