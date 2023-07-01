package com.example.todoapp.data.repositories

import android.content.Context
import android.util.Log
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.models.ToDoItemRequest
import com.example.todoapp.data.models.ToDoListRequest
import com.example.todoapp.data.models.ToDoListResponse
import com.example.todoapp.data.data_sources.networks.RetrofitInstance
import com.example.todoapp.data.data_sources.networks.ToDoApi
import com.example.todoapp.data.models.ToDoItemResponse
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.IOException

class NetworkRepository {
    private val api: ToDoApi = RetrofitInstance.api
    private var lastKnownRevision: Int = 0

    suspend fun getItem(id: String): Response<ToDoItemResponse> {
        return try {
            val response = makeRequestWithRetry(3, 1000) {
                api.getItem(id)
            }
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
            val response = makeRequestWithRetry(3, 1000) {
                api.addItem(lastKnownRevision, ToDoItemRequest(request))
            }
            return response.code()
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while adding item", e)
            throw e
        }
    }

    suspend fun updateItems(request: List<ToDoItem>): Int {
        try {
            val response = makeRequestWithRetry(3, 1000) {
                api.updateItems(lastKnownRevision, ToDoListRequest(request))
            }
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            return response.code()
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while updating items", e)
            throw e
        }

    }

    suspend fun getItems(): Response<ToDoListResponse> {
        return try {
            val response = makeRequestWithRetry(3, 1000) {
                api.getItems()
            }
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            response
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while getting items", e)
            throw e
        }
    }


    suspend fun updateItem(updatedItem: ToDoItem) : Int {
        try {
            val response = makeRequestWithRetry(3, 1000) {
                api.updateItem(lastKnownRevision, updatedItem.id, ToDoItemRequest(updatedItem))
            }
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            return response.code()
        } catch (e: Exception) {
            Log.e("NetworkRepository", "Exception occurred while updating item", e)
            throw e
        }

    }

    suspend fun deleteItem(id: String) : Int {
        try {
            val response = makeRequestWithRetry(3, 1000) {
                api.deleteItem(lastKnownRevision, id)
            }
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            return response.code()

        } catch (e: Exception) {
            Log.e("check", "Exception occurred while deleting item", e)
            throw e

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


