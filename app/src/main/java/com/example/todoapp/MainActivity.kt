package com.example.todoapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.todoapp.data.data_sources.networks.DataRefreshWorker
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.viewModels.ItemViewModel
import com.example.todoapp.ui.viewModels.ListViewModel
import com.example.todoapp.ui.viewModels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: ToDoRepository

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var connectivityManager : ConnectivityManager
    private lateinit var networkCallback : ConnectivityManager.NetworkCallback
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       (applicationContext as ToDoListApplication).appComponent.inject(this)

        checkInternetConnection()
        regularUpdate()
    }

    private fun checkInternetConnection() {
        val contextView = findViewById<View>(R.id.activityMain)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Snackbar.make(contextView, getString(R.string.internet_is_connected), Snackbar.LENGTH_LONG).show()
                lifecycleScope.launch(Dispatchers.IO) {
                    repository.updateItems()
                }
            }
            override fun onLost(network: Network) {
                Snackbar.make(contextView, getString(R.string.internet_is_not_connected), Snackbar.LENGTH_LONG).show()
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    private fun regularUpdate() {
        val refreshRequest = PeriodicWorkRequestBuilder<DataRefreshWorker>(
            repeatInterval = 8,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        ).build()
        WorkManager.getInstance(this).enqueue(refreshRequest)
    }

    override fun onDestroy() {
        super.onDestroy()

        connectivityManager.unregisterNetworkCallback(networkCallback)

    }

}