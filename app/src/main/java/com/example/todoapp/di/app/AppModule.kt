package com.example.todoapp.di.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.data_sources.local.ThemePreference
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.ui.viewModels.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("your_shared_prefs_name", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideViewModelFactory(
        repository: ToDoRepository,
        themePreference: ThemePreference
    ): ViewModelProvider.Factory {
        return ViewModelFactory(repository, themePreference)
    }
}