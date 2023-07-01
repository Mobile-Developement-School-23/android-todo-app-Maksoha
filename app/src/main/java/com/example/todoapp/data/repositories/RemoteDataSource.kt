package com.example.todoapp.data.repositories

import android.util.Log
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.models.ToDoItemRequest
import com.example.todoapp.data.models.ToDoListRequest
import com.example.todoapp.data.models.ToDoListResponse
import com.example.todoapp.data.data_sources.networks.RetrofitInstance
import com.example.todoapp.data.data_sources.networks.ToDoApi
import com.example.todoapp.data.models.ToDoItemResponse
import kotlinx.coroutines.delay
import retrofit2.Response

class RemoteDataSource {
    private val api: ToDoApi = RetrofitInstance.api
    private var lastKnownRevision: Int = 0

    suspend fun getItem(id: String): Response<ToDoItemResponse> {
        return try {
            val response = /*makeRequestWithRetry(1, 100) {*/
                api.getItem(id)
/*
            }
*/
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
        try {
            val response = /*makeRequestWithRetry(1, 100) {*/
                api.addItem(lastKnownRevision, ToDoItemRequest(request))
            /*}*/
            return response.code()
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while adding item", e)
            return 200
        }
    }

    suspend fun updateItems(request: List<ToDoItem>): Int {
        return try {
            val response = /*makeRequestWithRetry(1, 100) {*/
                api.updateItems(lastKnownRevision, ToDoListRequest(request))
            /*}*/
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            response.code()
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while updating items", e)
            200
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
            val response = /*makeRequestWithRetry(1, 100) {*/
                api.updateItem(lastKnownRevision, updatedItem.id, ToDoItemRequest(updatedItem))
            /*  }*/
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            response.code()
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while updating item", e)
            200
        }

    }

    suspend fun deleteItem(id: String) : Int {
        try {
            val response = /*makeRequestWithRetry(1, 100) {*/
                api.deleteItem(lastKnownRevision, id)
            /*}*/
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            return response.code()

        } catch (e: Exception) {
            Log.e("check", "Exception occurred while deleting item", e)
            return 200
        }
    }



    private suspend fun <T> makeRequestWithRetry(
        maxRetries: Int,
        retryIntervalMillis: Long,
        request: suspend () -> Response<T>
    ): Response<T> {
        var retryCount = 0
        var response: Response<T> = request()

        while (retryCount <= maxRetries) {
            try {
                response = request.invoke()

                if (response.isSuccessful) {
                    break
                } else {
                    if (retryCount == maxRetries) {
                        break
                    } else {
                        delay(retryIntervalMillis)
                    }
                }
            } catch (e: Exception) {
                // Handle the exception here, e.g., log it or do something else
                // You can also choose to retry or not based on the exception type
                if (retryCount == maxRetries) {
                    throw e
                } else {
                    delay(retryIntervalMillis)
                }
            }
            retryCount++
        }

        return response
    }


}


