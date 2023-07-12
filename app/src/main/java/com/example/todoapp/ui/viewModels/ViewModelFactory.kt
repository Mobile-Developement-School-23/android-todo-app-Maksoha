package com.example.todoapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.repositories.ToDoRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: ToDoRepository) :
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

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
