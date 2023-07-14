package com.example.todoapp.ui

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.todoapp.R
import com.example.todoapp.ToDoListApplication
import com.example.todoapp.data.data_sources.local.ThemePreference
import com.example.todoapp.data.repositories.ToDoRepository
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.di.activity.ActivityComponent
import com.example.todoapp.ui.model.applyTheme
import com.example.todoapp.ui.notifications.NotificationService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: ToDoRepository

    @Inject
    lateinit var themePreference: ThemePreference

    lateinit var activityComponent: ActivityComponent

    private lateinit var binding: ActivityMainBinding

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                themePreference.theme.collect { theme ->
                    applyTheme(theme)
                }
            }
        }
        activityComponent = (application as ToDoListApplication)
            .appComponent
            .activityComponent()
            .create()

        activityComponent.inject(this)
        checkInternetConnection()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }
    }

    private fun checkInternetConnection() {
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Snackbar.make(
                    binding.activityMain,
                    getString(R.string.internet_is_connected),
                    Snackbar.LENGTH_LONG
                )
                    .show()
                lifecycleScope.launch(Dispatchers.IO) {
                    repository.refreshData()
                }
            }

            override fun onLost(network: Network) {
                Snackbar.make(
                    binding.activityMain,
                    getString(R.string.internet_is_not_connected),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onDestroy() {
        super.onDestroy()

        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
