package com.example.todoapp.networks

import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.models.ToDoItemResponse
import com.example.todoapp.data.models.ToDoListResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.*

interface ToDoApi {

    @GET("/list")
    suspend fun getItems(): Response<ToDoListResponse>

    @PATCH("/list")
    suspend fun updateItems(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body request: List<ToDoItem>
    ): Response<ToDoListResponse>

    @GET("/list/{id}")
    suspend fun getItem(
        @Path("id") id: String
    ): Response<ToDoItemResponse>

    @POST("/list")
    suspend fun addItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body request: ToDoItem
    ): Response<ToDoItemResponse>

}

