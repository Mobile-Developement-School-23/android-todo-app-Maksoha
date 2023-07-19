package com.example.todoapp.data.data_sources.local.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem

@Entity
data class ToDoItemEntity(
    @PrimaryKey @ColumnInfo("id") val id: String,
    @ColumnInfo("text") val text: String,
    @ColumnInfo("importance") val importance: Importance,
    @ColumnInfo("deadline") val deadline: Long? = null,
    @ColumnInfo("done") var done: Boolean = false,
    @ColumnInfo("color") val color: String?,
    @ColumnInfo("createdAt") val createdAt: Long,
    @ColumnInfo("changedAt") val changedAt: Long,
    @ColumnInfo("lastUpdatedBy") val lastUpdatedBy: String
) {
    fun toDomainModel(): ToDoItem {
        return ToDoItem(
            id = id,
            text = text,
            importance = importance,
            deadline = deadline,
            done = done,
            color = color,
            createdAt = createdAt,
            changedAt = changedAt,
            lastUpdatedBy = lastUpdatedBy
        )
    }
}

