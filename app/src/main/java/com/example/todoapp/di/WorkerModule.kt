package com.example.todoapp.di

import android.content.Context
import android.content.ContextParams
import androidx.work.WorkerParameters
import com.example.todoapp.data.data_sources.networks.DataRefreshWorker
import com.example.todoapp.data.repositories.ToDoRepository
import dagger.Module
import dagger.Provides

@Module
class WorkerModule {

    @Provides
    fun provideDataRefreshWorker(
        appContext: Context,
        workerParams: WorkerParameters,
        repository: ToDoRepository
    ) : DataRefreshWorker {
        return DataRefreshWorker(appContext, workerParams, repository)
    }
}