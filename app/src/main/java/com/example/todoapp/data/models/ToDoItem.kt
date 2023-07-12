package com.example.todoapp.data.models

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R
import com.example.todoapp.data.data_sources.room.entities.ToDoItemEntity
import com.google.gson.annotations.SerializedName


data class ToDoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Long? = null,
    val done: Boolean = false,
    val color: String?,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("changed_at") val changedAt: Long,
    @SerializedName("last_updated_by") val lastUpdatedBy: String
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

enum class Importance {
    @SerializedName("low")
    LOW,
    @SerializedName("basic")
    COMMON,
    @SerializedName("important")
    HIGH
}

@Composable
fun Importance.convertToString(): String {
    return when (this) {
        Importance.LOW -> stringResource(R.string.low)
        Importance.COMMON -> stringResource(R.string.common)
        Importance.HIGH -> stringResource(R.string.high)
    }
}

fun Importance.toString(context: Context): String {
    return when (this) {
        Importance.LOW -> context.getString(R.string.low)
        Importance.COMMON -> context.getString(R.string.common)
        Importance.HIGH -> context.getString(R.string.high)
    }
}
