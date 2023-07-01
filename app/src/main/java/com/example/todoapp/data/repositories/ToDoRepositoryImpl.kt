package com.example.todoapp.data.repositories

import android.util.Log
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.utils.Constants
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ToDoRepository {

    override fun getItems(): Flow<List<ToDoItem>> {
        return localDataSource.getFlowItems()
    }

    override suspend fun refreshData() : Int {
        try {
            val response = remoteDataSource.getItems()
            if (response.isSuccess) {
                val bodyResponse = response.getOrNull()?.body()
                val code = response.getOrNull()?.code()
                if (bodyResponse != null) {
//                    localDataSource.clearDatabase()
                    localDataSource.updateItems(bodyResponse.list)
                }
                if (code != null) return code
                return Constants.SUCCESS_CODE
            }
            return Constants.SUCCESS_CODE

        } catch (e: Exception) {
            Log.e("check", "Exception occurred while refreshing data", e)
            return Constants.SUCCESS_CODE
        }
    }


    override fun getUndoneItems(): Flow<List<ToDoItem>> = localDataSource.getUndoneItems()

    override fun getItemById(itemId: String): ToDoItem = localDataSource.getItemById(itemId)


    override suspend fun updateItems() : Int {
        try {
            val updateItems = localDataSource.getItems()
            return remoteDataSource.updateItems(updateItems)
        } catch (e: Exception) {
            Log.e("check", "Exception occurred while updating items", e)
            throw e
        }
    }


    override suspend fun addItem(newItem: ToDoItem) : Int {
        try {
            localDataSource.addItem(newItem)
            return remoteDataSource.addItem(newItem)
        } catch (e: Exception) {
            Log.e("check", "Exception occurred while adding item", e)

            throw e
        }

    }

    override suspend fun updateItem(updatedItem: ToDoItem) : Int {
        try {
            localDataSource.updateItem(updatedItem)
            return remoteDataSource.updateItem(updatedItem)
        } catch (e: Exception) {
            Log.e("check", "Exception occurred while updating item", e)
            throw e
        }
    }

    override suspend fun deleteItem(itemId: String) : Int {
        try {
            localDataSource.deleteItem(itemId)
            return remoteDataSource.deleteItem(itemId)
        } catch (e: Exception) {
            Log.e("check", "Exception occurred while deleting item", e)
            throw e
        }

    }

    override suspend fun getNumberOfItems(): Flow<Int> = localDataSource.getNumberOfItems()
    override suspend fun getNumberOfDoneItems(): Flow<Int> = localDataSource.getNumberOfDoneItems()


}


