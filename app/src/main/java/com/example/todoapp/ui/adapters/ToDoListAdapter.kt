package com.example.todoapp.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.ItemTodoListBinding
import com.google.android.material.checkbox.MaterialCheckBox

class ToDoListAdapter(private val itemClickListener: OnItemClickListener) : ListAdapter<ToDoItem, ToDoListViewHolder>(ToDoListComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        return ToDoListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.text)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(current)
        }
        holder.itemView.findViewById<MaterialCheckBox>(R.id.checkbox).apply {
            setOnCheckedChangeListener { _, isChecked ->
                itemClickListener.onSwitchClick(current, isChecked)
            }
        }
        holder.itemView.findViewById<MaterialCheckBox>(R.id.checkbox).isChecked = current.isDone
        if (holder.itemView.findViewById<MaterialCheckBox>(R.id.checkbox).isChecked) {
            val paint: Paint = holder.itemView.findViewById<TextView>(R.id.text).paint
            paint.flags = paint.flags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: ToDoItem)
        fun onSwitchClick(item: ToDoItem, isChecked: Boolean)
    }

}

class ToDoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val toDoListItem: TextView = itemView.findViewById(R.id.text)


    fun bind(text: String?) {
        toDoListItem.text = text
    }

    companion object {
        fun create(parent: ViewGroup): ToDoListViewHolder {
            val binding = ItemTodoListBinding
                .inflate(LayoutInflater.from(parent.context), parent, false);
            val view: View = binding.root
            return ToDoListViewHolder(view)
        }
    }

}

class ToDoListComparator : DiffUtil.ItemCallback<ToDoItem>() {
    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }
}


