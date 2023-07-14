package com.example.todoapp.ui.screens.setting_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.data_sources.local.ThemePreference
import com.example.todoapp.ui.model.SettingAction
import com.example.todoapp.ui.model.Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(private val themePreference: ThemePreference) : ViewModel() {
    private val _state = MutableStateFlow(Theme.System)
    val state = _state.asStateFlow()

    private val _action = Channel<SettingAction>()
    val action = _action.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {themePreference.getTheme()}
        }
    }

    fun onAction(action : SettingAction) {
        when (action) {
            is SettingAction.UpdateTheme -> {
                _state.update { action.theme }
                themePreference.setTheme(action.theme)
            }

            SettingAction.Navigate -> viewModelScope.launch {
                _action.send(SettingAction.Navigate)
            }
        }

    }
}