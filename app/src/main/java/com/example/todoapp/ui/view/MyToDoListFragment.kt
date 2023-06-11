package com.example.todoapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.ToDoListApplication
import com.example.todoapp.data.models.ToDoListModel
import com.example.todoapp.databinding.FragmentMyToDoListBinding
import com.example.todoapp.ui.adapters.ToDoListAdapter
import com.example.todoapp.ui.viewModels.ToDoListViewModel
import com.example.todoapp.ui.viewModels.ToDoListViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MyToDoListFragment : Fragment(), ToDoListAdapter.OnItemClickListener {
    private lateinit var adapter: ToDoListAdapter
    private lateinit var binding : FragmentMyToDoListBinding
    private val viewModel: ToDoListViewModel by viewModels {
        ToDoListViewModelFactory((requireContext().applicationContext as ToDoListApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyToDoListBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        setRecyclerView()
        observeItems()


        return binding.root
    }

    private fun observeItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getItemsFlow().collect { items ->
                adapter.submitList(items)
                updateProgressIndicator(items)

            }
        }
    }


    private fun setRecyclerView() {
        adapter = ToDoListAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.btnAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
        }
    }

    private fun updateProgressIndicator(items: List<ToDoListModel>) {
        val completedItemsCount = items.count { it.isDone }
        binding.progressIndicator.max = items.size
        binding.progressIndicator.progress = completedItemsCount
        binding.stateProgressIndicator.text = "$completedItemsCount/${binding.progressIndicator.max}"
    }

    override fun onItemClick(item: ToDoListModel) {
        findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
    }

    override fun onSwitchClick(item: ToDoListModel, isChecked: Boolean) {
        viewModel.updateItem(item, isChecked)
    }


}