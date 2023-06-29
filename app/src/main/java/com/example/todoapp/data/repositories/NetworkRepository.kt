package com.example.todoapp.data.repositories

import android.util.Log
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.models.ToDoItemRequest
import com.example.todoapp.data.models.ToDoListRequest
import com.example.todoapp.data.models.ToDoListResponse
import com.example.todoapp.networks.RetrofitInstance
import com.example.todoapp.networks.ToDoApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

class NetworkRepository {
    private val api: ToDoApi = RetrofitInstance.api
    private var lastKnownRevision: Int = 0

    suspend fun getItem(id: String): ToDoItem? {
        try {
            val response = api.getItem(id)
            lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            if (response.isSuccessful) {
                return response.body()?.element
            } else {
                displayError(response.code())
            }
        } catch (e: Exception) {
            // Обработка ошибки при выполнении запроса
            // Вывод ошибки в журнал логов
            Log.e("check", "Ошибка при выполнении запроса на получение элемента", e)
        }
        return null
    }

    suspend fun addItem(request: ToDoItem) {
        try {
            val response = api.addItem(lastKnownRevision, ToDoItemRequest(request))
            lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            if (!response.isSuccessful) {
                displayError(response.code())
            }
        } catch (e: Exception) {
            // Обработка ошибки при выполнении запроса
            // Вывод ошибки в журнал логов
            Log.e("check", "Ошибка при выполнении запроса на добавление элемента", e)
        }
    }

    suspend fun updateItems(request: List<ToDoItem>): Response<ToDoListResponse>? {
        return try {
            val response = api.updateItems(lastKnownRevision, ToDoListRequest(request))
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            } else {
                displayError(response.code())
            }
            response
        } catch (e: IOException) {
            // Вывод ошибки в журнал логов
            Log.e("check", "Ошибка при выполнении запроса на обновление элементов списка", e)
            null
        }
    }


    suspend fun getItems(): List<ToDoItem> {
        return try {
            val response = api.getItems()
            lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            if (response.isSuccessful) {
                response.body()?.list ?: emptyList()
            } else {
                displayError(response.code())
                emptyList()
            }
        } catch (e: Exception) {
            // Вывод ошибки в журнал логов
            Log.e("check", "Ошибка при выполнении запроса на получение списка элементов", e)
            emptyList()
        }
    }


    suspend fun updateItem(updatedItem: ToDoItem) {
        try {
            val response = api.updateItem(lastKnownRevision, updatedItem.id, ToDoItemRequest(updatedItem))
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            } else {
                displayError(response.code())
            }
        } catch (e: Exception) {
            // Обработка ошибки при выполнении запроса
            if (e is IOException) {
                // Отсутствие интернета
            } else {
                // Вывод ошибки в журнал логов
                Log.e("check", "Ошибка при выполнении запроса на обновление элемента", e)
                throw e
            }
        }
    }


    suspend fun deleteItem(id: String) {
        try {
            val response = api.deleteItem(lastKnownRevision, id)
            lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            if (!response.isSuccessful) {
                displayError(response.code())
            }
        } catch (e: Exception) {
            // Обработка ошибки при выполнении запроса
            // Вывод ошибки в журнал логов
            Log.e("check", "Ошибка при выполнении запроса на удаление элемента", e)
        }
    }


    private fun displayError (error: Int) {
        when (error) {

        }
    }

}


