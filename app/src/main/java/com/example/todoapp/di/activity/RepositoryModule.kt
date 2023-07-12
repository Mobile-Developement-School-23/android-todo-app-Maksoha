package com.example.todoapp.di.activity

import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun bindToDoRepository(toDoRepositoryImpl: ToDoRepositoryImpl): ToDoRepository
}