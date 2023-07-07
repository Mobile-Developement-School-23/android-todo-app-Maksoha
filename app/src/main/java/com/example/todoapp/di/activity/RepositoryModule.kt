package com.example.todoapp.di.activity

import com.example.todoapp.data.data_sources.LocalDataSource
import com.example.todoapp.data.data_sources.RemoteDataSource
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideToDoRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): ToDoRepository = ToDoRepositoryImpl(localDataSource, remoteDataSource)
}