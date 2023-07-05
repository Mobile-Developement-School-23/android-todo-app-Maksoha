package com.example.todoapp.di

import android.app.Application
import com.example.todoapp.ToDoListApplication
import com.example.todoapp.data.data_sources.networks.ToDoApi
import com.example.todoapp.data.data_sources.room.root.AppDatabase
import com.example.todoapp.data.repositories.LocalDataSource
import com.example.todoapp.data.repositories.RemoteDataSource
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


@Module
class AppModule(private val application: ToDoListApplication) {

    @Provides
    fun provideApplication() : Application = application

    @Provides
    fun provideAppDatabase() : AppDatabase {
        val applicationScope = CoroutineScope(SupervisorJob())
        return AppDatabase.getDatabase(application, applicationScope)
    }

    @Provides
    fun provideRemoteDataSource(api : ToDoApi) : RemoteDataSource = RemoteDataSource(api)

    @Provides
    fun provideLocalDataSource(appDatabase: AppDatabase) : LocalDataSource =
        LocalDataSource(appDatabase.toDoItemDao())

    @Provides
    fun provideToDoRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): ToDoRepository = ToDoRepositoryImpl(localDataSource, remoteDataSource)
}