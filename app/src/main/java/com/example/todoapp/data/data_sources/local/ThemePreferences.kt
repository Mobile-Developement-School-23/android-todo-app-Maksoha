package com.example.todoapp.data.data_sources.local

import android.content.SharedPreferences
import com.example.todoapp.ui.model.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

const val THEME_KEY = "theme_key"

class ThemePreference @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val _theme : MutableStateFlow<Theme> = MutableStateFlow(getTheme())
    val theme : StateFlow<Theme> = _theme

    fun getTheme(): Theme {
        val themeValue = sharedPreferences.getInt(THEME_KEY, Theme.System.ordinal)
        return Theme.values().getOrElse(themeValue){Theme.System}
    }

    fun setTheme(theme: Theme) {
        sharedPreferences.edit().putInt(THEME_KEY, theme.ordinal).apply()
        _theme.update { theme }
    }
}