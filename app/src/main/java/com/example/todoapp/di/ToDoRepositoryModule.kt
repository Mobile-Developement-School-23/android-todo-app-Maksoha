package com.example.todoapp.di

import com.example.todoapp.data.repositories.LocalDataSource
import com.example.todoapp.data.repositories.RemoteDataSource
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class ToDoRepositoryModule {

    @Provides
    fun provideToDoRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): ToDoRepository = ToDoRepositoryImpl(localDataSource, remoteDataSource)
}