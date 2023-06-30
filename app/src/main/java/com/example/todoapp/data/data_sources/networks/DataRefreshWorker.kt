package com.example.todoapp.data.data_sources.networks

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.data.repositories.ToDoRepositoryImpl

class DataRefreshWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val repositoryImpl: ToDoRepositoryImpl
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        repositoryImpl.refreshData()
        
        return Result.success()
    }
}