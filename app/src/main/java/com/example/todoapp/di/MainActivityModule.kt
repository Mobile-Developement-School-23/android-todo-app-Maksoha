package com.example.todoapp.di

import com.example.todoapp.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun provideMainActivity() : MainActivity {
        return MainActivity()
    }
}