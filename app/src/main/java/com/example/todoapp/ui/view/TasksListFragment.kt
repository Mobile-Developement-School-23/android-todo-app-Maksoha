package com.example.todoapp.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.data.models.toString
import com.example.todoapp.databinding.FragmentListBinding
import com.example.todoapp.ui.MainActivity
import com.example.todoapp.ui.adapters.ToDoListAdapter
import com.example.todoapp.ui.screens.taskEdit_screen.TaskEditViewModel
import com.example.todoapp.ui.viewModels.TasksListViewModel
import com.example.todoapp.utils.ItemTouchHelperCallback
import com.example.todoapp.utils.SnackbarHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TasksListFragment : Fragment() {
    private lateinit var adapter: ToDoListAdapter
    private lateinit var binding: FragmentListBinding
    private lateinit var itemClickListener: ToDoListAdapter.OnItemClickListener

    private val taskEditViewModel: TaskEditViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }
    private val tasksListViewModel: TasksListViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        itemClickListener = object : ToDoListAdapter.OnItemClickListener {
            override fun onItemClick(item: ToDoItem) {
                taskEditViewModel.selectItem(item.id)
                findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
            }

            override fun onCheckboxClick(item: ToDoItem, isChecked: Boolean) {
                tasksListViewModel.updateItem(item.copy(done = isChecked))
            }

            override fun onButtonInfoClick(item: ToDoItem) {
                displayInformation(item)
            }

            override fun onItemLongClick(v: View?, item: ToDoItem) {
                displayMenu(v, item)
            }
        }
        (activity as MainActivity)
            .activityComponent
            .listFragmentComponent()
            .create()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                tasksListViewModel.refreshData()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
        binding.btnAddItem.setOnClickListener {
            taskEditViewModel.selectItem(null)
            findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
        }
        binding.btnVisibility.setOnClickListener {
            tasksListViewModel.changeStateVisibility()
        }
        binding.recyclerView.itemAnimator = null
        changeVisibility()
        updateProgressIndicator()
        displaySnackbar()
        setRecyclerView()
        setSwipeToDelete()
        observeData()
    }

    private fun displaySnackbar() {
        viewLifecycleOwner.lifecycleScope.launch {
            tasksListViewModel.getErrorState().collect { errorState ->
                SnackbarHelper(
                    requireActivity().findViewById(R.id.activityMain),
                    errorState,
                    tasksListViewModel.refreshData()
                ).showSnackbarWithAction()
            }
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            tasksListViewModel.items.collect { items ->
                adapter.submitList(items)
                binding.noTaskText.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun changeVisibility() {
        viewLifecycleOwner.lifecycleScope.launch {
            tasksListViewModel.visibility.collect { visibility ->
                binding.btnVisibility.setIconResource(
                    if (!visibility) R.drawable.outline_visibility_off_24
                    else R.drawable.outline_visibility_24
                )
            }
        }
    }

    private fun setRecyclerView() {
        adapter = ToDoListAdapter(itemClickListener)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        (binding.recyclerView.layoutManager as LinearLayoutManager).reverseLayout = true
    }

    private fun setSwipeToDelete() {
        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.round_delete_24)?.let {
            DrawableCompat.setTint(it, Color.WHITE)
            it
        }
        val background = ColorDrawable(Color.RED)
        val itemTouchCallback = ItemTouchHelperCallback({ position ->
            tasksListViewModel.deleteItem(adapter.currentList[position])
        }, icon, background)

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun updateProgressIndicator() {
        viewLifecycleOwner.lifecycleScope.launch {
            combine(tasksListViewModel.itemsSize, tasksListViewModel.doneItemsSize)
            { itemsSize, doneItemsSize ->
                Pair(itemsSize, doneItemsSize)
            }.collect { (itemsSize, doneItemsSize) ->
                binding.progressIndicator.max = itemsSize
                binding.progressIndicator.progress = doneItemsSize
                binding.stateProgressIndicator.text = "$doneItemsSize/$itemsSize"
            }
        }
    }

    private fun displayInformation(item: ToDoItem) {
        val deadline = item.deadline?.toString() ?: getString(R.string.no)
        val isDone = getString(if (item.done) R.string.yes else R.string.no)
        val createdAt = item.createdAt.toString()
        val changedAt = item.changedAt.toString()

        val itemDetails = getString(
            R.string.item_details, item.text,
            item.importance.toString(requireContext()),
            deadline, isDone, createdAt, changedAt
        )

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.information))
            .setMessage(itemDetails)
            .show()
    }

    private fun displayMenu(view: View?, item: ToDoItem) {
        val popupMenu = PopupMenu(view?.context, view)
        popupMenu.inflate(R.menu.item_action)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_information -> {
                    displayInformation(item)
                    true
                }

                R.id.action_edit -> {
                    taskEditViewModel.selectItem(item.id)
                    findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
                    true
                }

                R.id.action_delete -> {
                    tasksListViewModel.deleteItem(item)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }
}