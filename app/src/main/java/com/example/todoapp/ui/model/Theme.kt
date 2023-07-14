package com.example.todoapp.ui.model

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.todoapp.R

enum class Theme {
    Light,
    Dark,
    System
}

@Composable
fun Theme.ToString() : String {
    return when (this) {
        Theme.Light -> stringResource(id = R.string.light_theme)
        Theme.Dark -> stringResource(id = R.string.dark_theme)
        Theme.System -> stringResource(id = R.string.system_theme)
    }
}

fun applyTheme(theme: Theme) {
    when (theme) {
        Theme.Light -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        Theme.Dark -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        Theme.System -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}