package com.example.todoapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.data.repositories.ToDoRepositoryImpl
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.viewModels.ToDoItemViewModel
import com.example.todoapp.ui.viewModels.ToDoItemViewModelFactory
import com.example.todoapp.ui.viewModels.ToDoListViewModel
import com.example.todoapp.ui.viewModels.ToDoListViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val repository: ToDoRepositoryImpl by lazy {
        (applicationContext as ToDoListApplication).toDoRepository
    }
    val listViewModel: ToDoListViewModel by viewModels {
        ToDoListViewModelFactory(repository)
    }
    val itemViewModel: ToDoItemViewModel by viewModels {
        ToDoItemViewModelFactory(repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    override fun onResume() {
        super.onResume()

        val contextView = findViewById<View>(R.id.activityMain)
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
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

}