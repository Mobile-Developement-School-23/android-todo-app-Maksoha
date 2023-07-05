package com.example.todoapp.data.data_sources.networks

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.data.repositories.ToDoRepositoryImpl

class DataRefreshWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val repository: ToDoRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        repository.refreshData()
        
        return Result.success()
    }
}