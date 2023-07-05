package com.example.todoapp.data.repositories

import android.util.Log
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.models.ToDoItemRequest
import com.example.todoapp.data.models.ToDoListRequest
import com.example.todoapp.data.models.ToDoListResponse
import com.example.todoapp.data.data_sources.networks.ToDoApi
import com.example.todoapp.data.models.ToDoItemResponse
import com.example.todoapp.utils.Constants
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: ToDoApi) {
    private var lastKnownRevision: Int = 0

    suspend fun getItem(id: String): Response<ToDoItemResponse> {
        return try {
            val response = api.getItem(id)

            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            response
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while getting item", e)
            throw e
        }
    }

    suspend fun addItem(request: ToDoItem) : Int {
        return try {
            val response = api.addItem(lastKnownRevision, ToDoItemRequest(request))
            response.code()
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while adding item", e)
            Constants.FAILED_CONNECTION_CODE
        }
    }

    suspend fun updateItems(request: List<ToDoItem>): Int {
        return try {
            val response = api.updateItems(lastKnownRevision, ToDoListRequest(request))
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            response.code()
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while updating items", e)
            Constants.FAILED_CONNECTION_CODE
        }

    }

    suspend fun getItems(): Result<Response<ToDoListResponse>> {
        return try {
            val response = api.getItems()
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            Result.success(response)
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while getting items", e)
            Result.failure(e)
        }
    }



    suspend fun updateItem(updatedItem: ToDoItem) : Int {
        return try {
            val response = api.updateItem(lastKnownRevision, updatedItem.id, ToDoItemRequest(updatedItem))
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            response.code()
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while updating item", e)
            Constants.FAILED_CONNECTION_CODE
        }

    }

    suspend fun deleteItem(id: String) : Int {
        return try {
            val response = api.deleteItem(lastKnownRevision, id)
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            response.code()

        } catch (e: Exception) {
            Log.e("check", "Exception occurred while deleting item", e)
            Constants.FAILED_CONNECTION_CODE
        }
    }

}


