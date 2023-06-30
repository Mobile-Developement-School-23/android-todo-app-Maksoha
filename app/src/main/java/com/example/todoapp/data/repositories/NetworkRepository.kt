package com.example.todoapp.data.repositories

import android.util.Log
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.models.ToDoItemRequest
import com.example.todoapp.data.models.ToDoListRequest
import com.example.todoapp.data.models.ToDoListResponse
import com.example.todoapp.data.data_sources.networks.RetrofitInstance
import com.example.todoapp.data.data_sources.networks.ToDoApi
import kotlinx.coroutines.delay
import retrofit2.Response
import java.io.IOException

class NetworkRepository {
    private val api: ToDoApi = RetrofitInstance.api
    private var lastKnownRevision: Int = 0

    suspend fun getItem(id: String): ToDoItem? {
        val response = makeRequestWithRetry(3, 1000) {
            api.getItem(id)
        }
        if (response.isSuccessful) {
            lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            return response.body()?.element
        } else {
            // Handle server error
            val errorCode = response.code()
            // ...
            return null
        }
    }

    suspend fun addItem(request: ToDoItem) {
        val response = makeRequestWithRetry(3, 1000) {
            api.addItem(lastKnownRevision, ToDoItemRequest(request))
        }
        if (!response.isSuccessful) {
            // Handle server error
            val errorCode = response.code()
            // ...
        }
    }

    suspend fun updateItems(request: List<ToDoItem>): Response<ToDoListResponse>? {
        return try {
            val response = makeRequestWithRetry(3, 1000) {
                api.updateItems(lastKnownRevision, ToDoListRequest(request))
            }
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            } else {
                // Handle server error
                val errorCode = response.code()
                // ...
            }
            response
        } catch (e: IOException) {
            // Log the error here
            Log.e("check", "IOException occurred while updating items", e)
            null
        }
    }

    suspend fun getItems(): List<ToDoItem> {
        return try {
            val response = makeRequestWithRetry(3, 1000) {
                api.getItems()
            }
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            response.body()?.list ?: emptyList()
        } catch (e: Exception) {
            // Log the error here
            Log.e("check", "Exception occurred while getting items", e)
            emptyList()
        }
    }

    suspend fun updateItem(updatedItem: ToDoItem) {
        try {
            val response = makeRequestWithRetry(3, 1000) {
                api.updateItem(lastKnownRevision, updatedItem.id, ToDoItemRequest(updatedItem))
            }
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            } else {
                // Handle server error
                val errorCode = response.code()
                // ...
            }
        } catch (e: Exception) {
            // Handle request execution error
            if (e is IOException) {
                // No internet connection
            } else {
                // Log the error here
                Log.e("check", "Exception occurred while updating item", e)
                throw e
            }
        }
    }

    suspend fun deleteItem(id: String) {
        try {
            val response = makeRequestWithRetry(3, 1000) {
                api.deleteItem(lastKnownRevision, id)
            }
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }

        } catch (e: Exception) {
            // Handle request execution error
            // Log the error here
            Log.e("check", "Exception occurred while deleting item", e)
        }
    }


    private suspend fun <T> makeRequestWithRetry(
        maxRetries: Int,
        retryIntervalMillis: Long,
        request: suspend () -> Response<T>
    ): Response<T> {
        var retryCount = 0
        var response: Response<T>? = null

        while (retryCount <= maxRetries) {
            try {
                response = request.invoke()

                if (response.isSuccessful) {
                    // Successful response from the server
                    break
                } else {
                    // Error response from the server
                    if (retryCount == maxRetries) {
                        // Reached maximum retries, exit the loop
                        break
                    } else {
                        // Pause before retrying
                        delay(retryIntervalMillis)
                    }
                }
            } catch (e: Exception) {
                // Exception occurred
                if (retryCount == maxRetries) {
                    // Reached maximum retries, exit the loop
                    break
                } else {
                    // Pause before retrying
                    delay(retryIntervalMillis)
                }
            }

            retryCount++
        }

        return response ?: throw IllegalStateException("Response is null")
    }


}


