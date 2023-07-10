package com.example.todoapp.di.fragments

import androidx.lifecycle.ViewModel
import com.example.todoapp.ui.viewModels.ItemViewModel
import dagger.Binds
import dagger.Module

@Module
interface ItemViewModelModule {
    @Binds
    fun bindItemViewModel(itemViewModel: ItemViewModel): ViewModel
}