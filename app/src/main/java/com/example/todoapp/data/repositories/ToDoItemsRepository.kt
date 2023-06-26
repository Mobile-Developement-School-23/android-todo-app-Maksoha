package com.example.todoapp.data.repositories

import android.util.Log
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.models.ToDoItemRequest
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

class ToDoItemsRepository {
    private val api: ToDoApi = RetrofitInstance.api
    private var lastKnownRevision: Int = 0


    suspend fun getItem(id: String): ToDoItem? {
        try {
            val response = api.getItem(id)
            lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            if (response.isSuccessful) {
                return response.body()?.element
            } else displayError(response.code())

        } catch (e: Exception) {
            // Обработка ошибки при выполнении запроса
        }
        return null
    }


    suspend fun addItem(request: ToDoItem) {
        val response = api.addItem(lastKnownRevision, ToDoItemRequest("ok", request))
        lastKnownRevision = response.body()?.revision ?: lastKnownRevision

        if (!response.isSuccessful) {
            displayError(response.code())
        }
    }



    suspend fun updateItems(request: List<ToDoItem>): Response<ToDoListResponse> {
        try {
            val response = api.updateItems(lastKnownRevision, request)
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            } else {
                displayError(response.code())

            }
            return response
        } catch (e: Exception) {
            throw e
        }
    }


    fun getItems(): Flow<List<ToDoItem>> = callbackFlow {
        val response = api.getItems()
        lastKnownRevision = response.body()?.revision ?: lastKnownRevision
        if (response.isSuccessful) {
            val itemList = response.body()?.list ?: emptyList()
            trySend(itemList)
            close()
        } else {
            displayError(response.code())

        }
    }.flowOn(Dispatchers.IO)

    suspend fun updateItem(item: ToDoItem) {
        try {
            val response = api.updateItem(lastKnownRevision, item.id, ToDoItemRequest("ok", item))
            if (response.isSuccessful) {
                lastKnownRevision = response.body()?.revision ?: lastKnownRevision
            }
            else displayError(response.code())

        } catch (e: Exception) {
            throw e
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
        }
    }

    private fun displayError (error: Int) {
        when (error) {

        }
    }


//    fun getCompletedItems(): Flow<List<ToDoItem>> = callbackFlow {
//        val response = api.getCompletedItems(done = true)
//        lastKnownRevision = response.body()?.revision ?: lastKnownRevision
//        if (response.isSuccessful) {
//            val itemList = response.body()?.list ?: emptyList()
//            trySend(itemList)
//            close()
//        } else {
//            close(CancellationException("Failed to get items: ${response.code()}"))
//        }
//    }.flowOn(Dispatchers.IO)






    //
//    private suspend fun loadData() {
//        val response = api.getItems()
//        if (response.isSuccessful) {
//            val listResponse = response.body()
//            if (listResponse != null) {
//                lastKnownRevision = listResponse.revision
//            }
//        }
//
//        addItem(ToDoItem(
//            "item_1",
//            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.HIGH,
//            1677084800000,
//            false,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//        addItem(ToDoItem(
//            "item_2",
//            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.COMMON,
//            167708480000,
//            true,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//        addItem(ToDoItem(
//            "item_3",
//            "Бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.LOW,
//            1677084800000,
//            false,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//        addItem(ToDoItem(
//            "item_4",
//            "Оооооооооооооооооооочееееееееенььььььььььььььь бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.COMMON,
//            null,
//            false,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//        addItem(ToDoItem(
//            "item_5",
//            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.HIGH,
//            1677084800000,
//            true,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//        addItem(ToDoItem(
//            "item_6",
//            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.COMMON,
//            1677084800000,
//            false,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//        addItem(ToDoItem(
//            "item_7",
//            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.LOW,
//            null,
//            false,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//        addItem(ToDoItem(
//            "item_8",
//            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.COMMON,
//            1677084800000,
//            true,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//        addItem(ToDoItem(
//            "item_9",
//            "Оооооооооооооооооооочееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.HIGH,
//            1677084800000,
//            false,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//        addItem(ToDoItem(
//            "item_10",
//            "Оооооооооооооооооооочееееееееееееееееееееееееееень бооооооольшооооооооооооой тексссссссссссссссссссссст",
//            Importance.COMMON,
//            1677084800000,
//            true,
//            null,
//            1672483200000,
//            1672483200000,
//            ""
//        ))
//    }
}


