package com.example.todoapp.utils

import java.time.LocalDate
import java.time.ZoneOffset
import android.provider.Settings

class Constants {
    companion object {
        const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
        const val TOKEN = "turfed"
        const val FAILED_CONNECTION_CODE = -1
        val todayLocalDate : LocalDate
            get() = LocalDate.now(ZoneOffset.UTC)

    }
}