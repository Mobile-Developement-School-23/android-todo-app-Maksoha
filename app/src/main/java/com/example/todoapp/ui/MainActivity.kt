package com.example.todoapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.ToDoListApplication
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.di.activity.ActivityComponent
import com.example.todoapp.ui.viewModels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: ToDoRepository
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var activityComponent: ActivityComponent

    private lateinit var binding : ActivityMainBinding

    private lateinit var connectivityManager : ConnectivityManager
    private lateinit var networkCallback : ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityComponent = (applicationContext as ToDoListApplication)
            .appComponent
            .activityComponent()
            .create(this)

        activityComponent.inject(this)
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Snackbar.make(binding.activityMain, getString(R.string.internet_is_connected), Snackbar.LENGTH_LONG)
                    .show()
                lifecycleScope.launch(Dispatchers.IO) {
                    repository.updateItems()
                }
            }

            override fun onLost(network: Network) {
                Snackbar.make(binding.activityMain, getString(R.string.internet_is_not_connected), Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onDestroy() {
        super.onDestroy()

        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
