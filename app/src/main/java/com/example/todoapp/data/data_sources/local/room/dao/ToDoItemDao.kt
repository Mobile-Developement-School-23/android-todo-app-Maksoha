package com.example.todoapp.data.data_sources.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.data_sources.local.room.entities.ToDoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoItemDao {

    @Query("DELETE FROM ToDoItemEntity")
    suspend fun deleteAllItems()

    @Query("SELECT * FROM ToDoItemEntity")
    fun getItems(): List<ToDoItemEntity>

    @Query("SELECT COUNT(*) FROM ToDoItemEntity")
    fun getNumberOfItems(): Flow<Int>

    @Query("SELECT COUNT(*) FROM ToDoItemEntity WHERE done = 1")
    fun getNumberOfDoneItems(): Flow<Int>

    @Query("SELECT * FROM ToDoItemEntity")
    fun getFlowItems(): Flow<List<ToDoItemEntity>>

    @Query("SELECT * FROM ToDoItemEntity WHERE done = 0")
    fun getUndoneItems(): Flow<List<ToDoItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItems(newItems: List<ToDoItemEntity>)

    @Query("SELECT * FROM ToDoItemEntity WHERE id = :itemId")
    fun getItemById(itemId: String): ToDoItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(newItem: ToDoItemEntity)

    @Update
    suspend fun updateItem(newItem: ToDoItemEntity)

    @Query("DELETE FROM ToDoItemEntity WHERE id = :itemId")
    suspend fun deleteItemById(itemId: String)

    @Query("SELECT * FROM ToDoItemEntity WHERE deadline = :time AND done = 0")
    fun getDeadlineItems(time : Long) : List<ToDoItemEntity>

}