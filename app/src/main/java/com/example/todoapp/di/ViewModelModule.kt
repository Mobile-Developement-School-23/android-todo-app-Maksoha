package com.example.todoapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.MainActivity
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.ui.viewModels.ItemViewModel
import com.example.todoapp.ui.viewModels.ListViewModel
import com.example.todoapp.ui.viewModels.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideViewModelFactory(repository: ToDoRepository): ViewModelProvider.Factory {
        return ViewModelFactory(repository)
    }
}