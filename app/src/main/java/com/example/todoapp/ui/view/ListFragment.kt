package com.example.todoapp.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.ToDoListApplication
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.FragmentMyToDoListBinding
import com.example.todoapp.ui.adapters.ToDoListAdapter
import com.example.todoapp.ui.viewModels.ItemViewModel
import com.example.todoapp.ui.viewModels.ListViewModel
import com.example.todoapp.ui.viewModels.ViewModelFactory
import com.example.todoapp.utils.Converters
import com.example.todoapp.utils.ItemTouchHelperCallback
import com.example.todoapp.utils.SnackbarHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListFragment : Fragment() {
    private lateinit var adapter: ToDoListAdapter
    private val converters = Converters()
    private lateinit var binding: FragmentMyToDoListBinding
    private lateinit var itemClickListener: ToDoListAdapter.OnItemClickListener
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val itemViewModel: ItemViewModel by activityViewModels{viewModelFactory}
    private val listViewModel : ListViewModel by activityViewModels{viewModelFactory}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyToDoListBinding.inflate(layoutInflater, container, false)
        itemClickListener = createItemClickListener()
        binding.recyclerView.itemAnimator = null

        val appComponent = (requireActivity().application as ToDoListApplication).appComponent
        appComponent.inject(this)
        setRecyclerView()
        observeData()
        changeVisibility()
        updateProgressIndicator()
        displaySnackbar()
        binding.btnAddItem.setOnClickListener {
            itemViewModel.selectItem(null)
            findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
        }
        binding.btnVisibility.setOnClickListener {
            listViewModel.changeStateVisibility()
        }
        return binding.root
    }

    private fun displaySnackbar() {
        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.getErrorState().collect { errorState ->
                SnackbarHelper(
                    requireActivity().findViewById(R.id.activityMain),
                    errorState,
                    listViewModel.refreshData()
                ).showSnackbarWithAction()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewLifecycleOwner.lifecycleScope.launch {
                listViewModel.refreshData()
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.items.collect { items ->
                adapter.submitList(items)
                binding.noTaskText.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun changeVisibility() {
        viewLifecycleOwner.lifecycleScope.launch {
            listViewModel.visibility.collect { visibility ->
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
        setSwipeToDelete()
    }

    private fun setSwipeToDelete() {
        val icon = ContextCompat.getDrawable(requireContext(), R.drawable.round_delete_24)?.let {
            DrawableCompat.setTint(it, Color.WHITE)
            it
        }
        val background = ColorDrawable(Color.RED)
        val itemTouchCallback = ItemTouchHelperCallback({ position ->
            listViewModel.deleteItem(adapter.currentList[position])
        }, icon, background)

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun updateProgressIndicator() {
        viewLifecycleOwner.lifecycleScope.launch {
            combine(listViewModel.itemsSize, listViewModel.doneItemsSize)
            { itemsSize, doneItemsSize ->
                Pair(itemsSize, doneItemsSize)
            }.collect { (itemsSize, doneItemsSize) ->
                binding.progressIndicator.max = itemsSize
                binding.progressIndicator.progress = doneItemsSize
                binding.stateProgressIndicator.text = "$doneItemsSize/$itemsSize"
            }
        }
    }

    private fun createItemClickListener(): ToDoListAdapter.OnItemClickListener {
        return object : ToDoListAdapter.OnItemClickListener {
            override fun onItemClick(item: ToDoItem) {
                editTask(item)
            }

            override fun onCheckboxClick(item: ToDoItem, isChecked: Boolean) {
                listViewModel.updateItem(item.copy(done = isChecked))
            }

            override fun onButtonInfoClick(item: ToDoItem) {
                displayInformation(item)
            }

            override fun onItemLongClick(v: View?, item: ToDoItem) {
                displayMenu(v, item)
            }
        }
    }

    private fun editTask(item: ToDoItem) {
        itemViewModel.selectItem(item.id)
        findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
    }

    private fun displayInformation(item: ToDoItem) {
        val deadline = item.deadline?.let { converters.convertLongToStringDate(it) } ?: getString(R.string.no_text)
        val isDone = getString(if (item.done) R.string.yes_text else R.string.no_text)
        val createdAt = converters.convertLongToStringDate(item.createdAt)
        val changedAt = converters.convertLongToStringDate(item.changedAt)

        val itemDetails = getString(
            R.string.item_details, item.text,
            converters.convertImportanceToString(item.importance, requireContext()),
            deadline, isDone, createdAt, changedAt
        )

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.information_text))
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
                    editTask(item)
                    true
                }
                R.id.action_delete -> {
                    listViewModel.deleteItem(item)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}