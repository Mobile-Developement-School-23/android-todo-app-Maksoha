package com.example.todoapp.ui.model

import com.example.todoapp.data.models.Importance

sealed class SettingAction {
    data class UpdateTheme(val theme: Theme) : SettingAction()
    object Navigate : SettingAction()
}