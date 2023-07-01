package com.example.todoapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {
    companion object {
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

    }


}