package com.example.todoapp.utils

import android.content.Context
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {
        fun convertLongToStringDate(timestamp: Long): String {
            val date = Date(timestamp)
            val format = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
            return format.format(date)
        }

        fun convertStringToLongDate(dateString: String): Long {
            val format = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
            val date = format.parse(dateString)
            return date?.time ?: 0L
        }

        fun convertImportanceToString(importance: Importance, context: Context): String {
            return when (importance) {
                Importance.LOW -> context.getString(R.string.low_text)
                Importance.COMMON -> context.getString(R.string.common_text)
                Importance.HIGH -> context.getString(R.string.high_text)
            }
        }

        fun convertStringToImportance(string: String, context: Context) : Importance {
            return when (string) {
                context.getString(R.string.low_text) -> Importance.LOW
                context.getString(R.string.common_text) -> Importance.COMMON
                context.getString(R.string.high_text) -> Importance.HIGH
                else -> Importance.COMMON
            }
        }
}
