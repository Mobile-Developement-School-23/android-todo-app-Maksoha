package com.example.todoapp.data.models

import com.google.gson.annotations.SerializedName



data class ToDoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Long? = null,
    var done: Boolean = false,
    val color: String?,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("changed_at")
    val changedAt: Long,
    @SerializedName("last_updated_by")
    val lastUpdatedBy : String
)
enum class Importance {
    @SerializedName("low")
    LOW,
    @SerializedName("basic")
    COMMON,
    @SerializedName("important")
    HIGH
}
