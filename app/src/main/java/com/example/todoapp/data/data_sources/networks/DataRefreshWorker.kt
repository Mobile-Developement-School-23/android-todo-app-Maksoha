package com.example.todoapp.data.data_sources.networks

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.todoapp.data.repositories.ToDoRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DataRefreshWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: ToDoRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        repository.refreshData()
        return Result.success()
    }

    companion object {
        fun startRefresh(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val syncDataRequest = PeriodicWorkRequestBuilder<DataRefreshWorker>(
                repeatInterval = 8,
                repeatIntervalTimeUnit = TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueue(syncDataRequest)
        }
    }
}