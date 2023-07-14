package com.example.todoapp.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertToStringDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    return format.format(date)
}

fun String.convertToLongDate(): Long {
    val format = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    val date = format.parse(this)
    return date?.time ?: 0L
}

fun String.convertToImportance(context: Context): Importance {
    return when (this) {
        context.getString(R.string.low_text) -> Importance.LOW
        context.getString(R.string.common_text) -> Importance.COMMON
        context.getString(R.string.high_text) -> Importance.HIGH
        else -> Importance.COMMON
    }
}

fun Importance.convertToString(context: Context): String {
    return when (this) {
        Importance.LOW -> context.getString(R.string.low_text)
        Importance.COMMON -> context.getString(R.string.common_text)
        Importance.HIGH -> context.getString(R.string.high_text)
    }
}

@Composable
fun Importance.convertToString(): String {
    return when (this) {
        Importance.LOW -> stringResource(R.string.low_text)
        Importance.COMMON -> stringResource(R.string.common_text)
        Importance.HIGH -> stringResource(R.string.high_text)
    }
}
