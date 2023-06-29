package com.example.todoapp.data.models

import com.example.todoapp.data.data_sources.room.entities.ToDoItemEntity
import com.google.gson.annotations.SerializedName



data class ToDoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Long? = null,
    var done: Boolean = false,
    val color: String?,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("changed_at") val changedAt: Long,
    @SerializedName("last_updated_by") val lastUpdatedBy : String
) {
    fun toEntity(): ToDoItemEntity {
        return ToDoItemEntity(
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
enum class Importance() {
    @SerializedName("low") LOW,
    @SerializedName("basic") COMMON,
    @SerializedName("important") HIGH
}
