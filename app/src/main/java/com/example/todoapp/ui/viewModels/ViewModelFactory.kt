package com.example.todoapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.data_sources.local.ThemePreference
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.ui.screens.setting_screen.SettingViewModel
import com.example.todoapp.ui.screens.taskEdit_screen.TaskEditViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: ToDoRepository, private val themePreference: ThemePreference) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TasksListViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                TasksListViewModel(repository) as T
            }

            modelClass.isAssignableFrom(TaskEditViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                TaskEditViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                SettingViewModel(themePreference) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

