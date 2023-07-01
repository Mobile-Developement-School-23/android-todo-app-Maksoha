package com.example.todoapp.data.repositories

import android.util.Log
import com.example.todoapp.data.models.ToDoItem
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(
    private val localRepository: LocalRepository,
    private val networkRepository: NetworkRepository
) : ToDoRepository {

    override fun getItems(): Flow<List<ToDoItem>> {
        return localRepository.getFlowItems()
    }

    override suspend fun refreshData() : Int {
        try {
            val response = networkRepository.getItems()
            val bodyResponse = response.body()
            val code = response.code()
            if (bodyResponse != null) {
                localRepository.clearDatabase()
                localRepository.updateItems(bodyResponse.list)
            }
            return code

        } catch (e: Exception) {
            Log.e("check", "Exception occurred while refreshing data", e)
            throw e
        }
    }


    override fun getUndoneItems(): Flow<List<ToDoItem>> = localRepository.getUndoneItems()

    override fun getItemById(itemId: String): ToDoItem = localRepository.getItemById(itemId)


    override suspend fun updateItems() : Int {
        try {
            val updateItems = localRepository.getItems()
            return networkRepository.updateItems(updateItems)
        } catch (e: Exception) {
            Log.e("check", "Exception occurred while updating items", e)
            throw e
        }
    }


    override suspend fun addItem(newItem: ToDoItem) : Int {
        try {
            localRepository.addItem(newItem)
            return networkRepository.addItem(newItem)
        } catch (e: Exception) {
            Log.e("check", "Exception occurred while adding item", e)

            throw e
        }

    }

    override suspend fun updateItem(updatedItem: ToDoItem) : Int {
        try {
            localRepository.updateItem(updatedItem)
            val code = networkRepository.updateItem(updatedItem)
            updateItems()
            return code
        } catch (e: Exception) {
            Log.e("check", "Exception occurred while updating item", e)
            throw e
        }
    }

    override suspend fun deleteItem(itemId: String) : Int {
        try {
            localRepository.deleteItem(itemId)
            return networkRepository.deleteItem(itemId)
        } catch (e: Exception) {
            Log.e("check", "Exception occurred while deleting item", e)
            throw e
        }

    }

    override suspend fun getNumberOfItems(): Flow<Int> = localRepository.getNumberOfItems()
    override suspend fun getNumberOfDoneItems(): Flow<Int> = localRepository.getNumberOfDoneItems()

    fun displayErrorCode(code : Int) {

    }

}


