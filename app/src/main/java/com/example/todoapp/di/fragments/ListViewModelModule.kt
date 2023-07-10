package com.example.todoapp.di.fragments

import androidx.lifecycle.ViewModel
import com.example.todoapp.ui.viewModels.ListViewModel
import dagger.Binds
import dagger.Module

@Module
interface ListViewModelModule {
    @Binds
    fun bindListViewModel(listViewModel: ListViewModel): ViewModel

}