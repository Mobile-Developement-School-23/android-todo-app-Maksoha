package com.example.todoapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


class SnackbarHelper(private val view: View, private val errorCode: Int, private val action: Unit) {

    fun showSnackbarWithAction() {
        val errorMessage = getErrorMessage()
        val snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
        snackbar.setAction("Обновить") {
            action
        }
        if (errorCode != 200) {
            snackbar.show()

        }
    }

    private fun getErrorMessage(): String {
        return when (errorCode) {
            400 -> "Ошибка синхронизации. Попробуйте обновить данные."
            401 -> "Неверная авторизация"
            404 -> "Такой элемент на сервере не найден"
            500 -> "Произошла ошибка сервера"
            else -> "Неизвестная ошибка"
        }
    }
}

