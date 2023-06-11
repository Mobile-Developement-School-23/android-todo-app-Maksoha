package com.example.todoapp.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import com.example.todoapp.ToDoListApplication
import com.example.todoapp.data.repositories.ToDoListRepository
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.viewModels.ToDoItemViewModel
import com.example.todoapp.ui.viewModels.ToDoItemViewModelFactory
import com.example.todoapp.ui.viewModels.ToDoListViewModel
import com.example.todoapp.ui.viewModels.ToDoListViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val repository: ToDoListRepository by lazy {
        (applicationContext as ToDoListApplication).repository
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
}