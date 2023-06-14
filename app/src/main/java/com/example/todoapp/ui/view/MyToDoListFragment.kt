package com.example.todoapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.FragmentMyToDoListBinding
import com.example.todoapp.ui.adapters.ToDoListAdapter
import com.example.todoapp.ui.viewModels.ToDoItemViewModel
import com.example.todoapp.ui.viewModels.ToDoListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch


class MyToDoListFragment : Fragment() {
    private lateinit var adapter: ToDoListAdapter
    private lateinit var binding : FragmentMyToDoListBinding
    private lateinit var itemClickListener: ToDoListAdapter.OnItemClickListener
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
        itemClickListener = createItemClickListener()
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
            if (items.isNullOrEmpty()) {
                binding.noTaskText.visibility = View.VISIBLE
            }
            else {
                binding.noTaskText.visibility = View.INVISIBLE
            }
            adapter.submitList(items)
            updateProgressIndicator(items)
        }

    }


    private fun setRecyclerView() {
        adapter = ToDoListAdapter(itemClickListener)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        (binding.recyclerView.layoutManager as LinearLayoutManager).reverseLayout = true

        setSwipe()

    }

    private fun setSwipe() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                listViewModel.deleteItemByPosition(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun updateProgressIndicator(items: List<ToDoItem>) {
        val completedItemsCount = items.count { it.isDone }
        binding.progressIndicator.max = items.size
        binding.progressIndicator.progress = completedItemsCount
        binding.stateProgressIndicator.text = "$completedItemsCount/${binding.progressIndicator.max}"
    }

    private fun createItemClickListener(): ToDoListAdapter.OnItemClickListener {
        return object : ToDoListAdapter.OnItemClickListener {
            override fun onItemClick(item: ToDoItem) {
                editTask(item)
            }

            override fun onSwitchClick(item: ToDoItem, isChecked: Boolean) {
                listViewModel.updateItem(item, item.copy(isDone = isChecked))
            }

            override fun onButtonInfoClick(item: ToDoItem) {
                displayInformation(item)
            }

            override fun onItemSwipedLeft(item: ToDoItem) {
                listViewModel.deleteItem(item)
            }


            override fun onItemLongClick(v: View?, item: ToDoItem) {
                displayMenu(v, item)
            }
        }
    }

    private fun editTask(item: ToDoItem) {
        itemViewModel.setItem(item)
        findNavController().navigate(R.id.action_myToDoListFragment_to_addToDoItemFragment)
    }

    private fun displayInformation(item: ToDoItem) {
        val deadline = if (item.deadline.isNullOrEmpty()) "Нет" else item.deadline
        val isDone = if (item.isDone) "Да" else "Нет"
        val message = "Дело: ${item.text}\nВажность: ${item.importance}\nДедлайн: $deadline\n" +
                "Выполнено: $isDone\nДата создания: ${item.creation_date}\nДата изменения: ${item.change_date}"
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Информация")
            .setMessage(message)
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