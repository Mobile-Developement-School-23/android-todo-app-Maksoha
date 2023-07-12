package com.example.todoapp.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.ItemTodoListBinding

class ToDoListAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<ToDoItem, ToDoListViewHolder>(ToDoListComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        return ToDoListViewHolder.create(parent, itemClickListener)
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    interface OnItemClickListener {
        fun onItemClick(item: ToDoItem)
        fun onCheckboxClick(item: ToDoItem, isChecked: Boolean)
        fun onItemLongClick(v: View?, item: ToDoItem)
        fun onButtonInfoClick(item: ToDoItem)
    }
}


class ToDoListViewHolder(
    private val binding: ItemTodoListBinding,
    private val itemClickListener: ToDoListAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var currentItem: ToDoItem

    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            itemClickListener.onCheckboxClick(currentItem, isChecked)
        }
        binding.btnInfo.setOnClickListener {
            itemClickListener.onButtonInfoClick(currentItem)
        }
    }

    fun bind(item: ToDoItem) {
        currentItem = item
        binding.text.text = item.text
        binding.checkbox.isChecked = item.done

        binding.date.visibility = if (item.deadline != null) View.VISIBLE else View.GONE
        binding.date.text = item.deadline?.toString()

        binding.indicator.setBackgroundResource(
            if (item.importance == Importance.HIGH) R.color.md_theme_light_error
            else 0x00
        )

        val paintFlags = if (binding.checkbox.isChecked) Paint.STRIKE_THRU_TEXT_FLAG else 0
        binding.text.paint.flags =
            binding.text.paint.flags and Paint.STRIKE_THRU_TEXT_FLAG.inv() or paintFlags
    }

    companion object {
        fun create(
            parent: ViewGroup,
            itemClickListener: ToDoListAdapter.OnItemClickListener
        ): ToDoListViewHolder {
            val binding =
                ItemTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ToDoListViewHolder(binding, itemClickListener)
        }
    }

    override fun onClick(v: View?) {
        itemClickListener.onItemClick(currentItem)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener.onItemLongClick(v, currentItem)
        return true
    }
}

class ToDoListComparator : DiffUtil.ItemCallback<ToDoItem>() {
    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }
}




