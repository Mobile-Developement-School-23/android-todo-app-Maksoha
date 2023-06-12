package com.example.todoapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.FragmentMyToDoListBinding
import com.example.todoapp.ui.adapters.ToDoListAdapter
import com.example.todoapp.ui.viewModels.ToDoItemViewModel
import com.example.todoapp.ui.viewModels.ToDoListViewModel


class MyToDoListFragment : Fragment(), ToDoListAdapter.OnItemClickListener {
    private lateinit var adapter: ToDoListAdapter
    private lateinit var binding : FragmentMyToDoListBinding
    private val listViewModel: ToDoListViewModel by lazy {
        (requireActivity() as MainActivity).listViewModel
    }
    private val itemViewModel: ToDoItemViewModel by lazy {
        (requireActivity() as MainActivity).itemViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyToDoListBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        setRecyclerView()


        binding.btnAddItem.setOnClickListener {
            itemViewModel.setItem(null)
            findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeItems()

    }

    private fun observeItems() {
        listViewModel.getItems().observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
            updateProgressIndicator(items)
        }

    }


    private fun setRecyclerView() {
        adapter = ToDoListAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        (binding.recyclerView.layoutManager as LinearLayoutManager).reverseLayout = true
    }

    private fun updateProgressIndicator(items: List<ToDoItem>) {
        val completedItemsCount = items.count { it.isDone }
        binding.progressIndicator.max = items.size
        binding.progressIndicator.progress = completedItemsCount
        binding.stateProgressIndicator.text = "$completedItemsCount/${binding.progressIndicator.max}"
    }

    override fun onItemClick(item: ToDoItem) {
        itemViewModel.setItem(item)
        findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
    }

    override fun onSwitchClick(item: ToDoItem, isChecked: Boolean) {
        listViewModel.updateItem(item, item.copy(isDone = isChecked))
    }


}