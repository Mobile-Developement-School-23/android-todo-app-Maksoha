package com.example.todoapp.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date
import java.util.Locale

fun Long.toStringDate(): String {
    val date = Date(this)
    val format = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    return format.format(date)
}

fun String.toLong(): Long {
    val format = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    val date = format.parse(this)
    return date?.time ?: 0L
}

fun String.toImportance(context: Context): Importance {
    return when (this) {
        context.getString(R.string.low) -> Importance.LOW
        context.getString(R.string.common) -> Importance.COMMON
        context.getString(R.string.high) -> Importance.HIGH
        else -> Importance.COMMON
    }
}



fun LocalDate.toLong() : Long {
    val zoneId = ZoneOffset.UTC
    return atStartOfDay(zoneId).toEpochSecond() * 1000
}

fun LocalDate.toStringDate() : String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(this)


fun Long.toLocalDate() : LocalDate = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()


