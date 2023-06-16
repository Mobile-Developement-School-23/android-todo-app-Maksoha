package com.example.todoapp.ui.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        binding.btnVisibility.setOnClickListener {
            listViewModel.changeStateVisibility()
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeItems()

    }


    private fun observeItems() {
        listViewModel.getItems().observe(viewLifecycleOwner) { items ->
            listViewModel.getStateVisibility().observe(viewLifecycleOwner) { visibility ->
                if (!visibility && items != null) {
                    binding.btnVisibility.setIconResource(R.drawable.outline_visibility_off_24)
                    adapter.submitList(listViewModel.hide())
                    if (listViewModel.hide().isEmpty()) {
                        binding.noTaskText.visibility = View.VISIBLE
                    }
                    else {
                        binding.noTaskText.visibility = View.GONE
                    }
                }
                else {
                    binding.btnVisibility.setIconResource(R.drawable.outline_visibility_24)
                    if (items.isNullOrEmpty()) {
                        binding.noTaskText.visibility = View.VISIBLE
                    }
                    else {
                        binding.noTaskText.visibility = View.GONE
                    }
                    adapter.submitList(items)
                }
                updateProgressIndicator(items)
            }
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
        val itemTouchCallback = object : ItemTouchHelper.Callback() {
            private val background = ColorDrawable(Color.RED)
            private val icon = ContextCompat.getDrawable(requireContext(), R.drawable.round_delete_24)?.let {
                DrawableCompat.setTint(it, Color.WHITE)
                it
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.LEFT
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                val itemView = viewHolder.itemView
                val itemHeight = itemView.height
                val isSwipeLeft = dX < 0

                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(c)

                val iconMargin = (itemHeight - icon!!.intrinsicHeight) / 2
                val iconLeft = itemView.right + dX.toInt() + iconMargin
                val iconTop = itemView.top + (itemHeight - icon.intrinsicHeight) / 2
                val iconRight = iconLeft + icon.intrinsicWidth
                val iconBottom = iconTop + icon.intrinsicHeight

                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                icon.draw(c)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromIndex = viewHolder.adapterPosition
                val toIndex = target.adapterPosition

                listViewModel.moveItem(fromIndex, toIndex)

                return true
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