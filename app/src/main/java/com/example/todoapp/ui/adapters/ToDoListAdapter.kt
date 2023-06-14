package com.example.todoapp.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.ItemTodoListBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox

class ToDoListAdapter(private val itemClickListener: OnItemClickListener) : ListAdapter<ToDoItem, ToDoListViewHolder>(ToDoListComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        return ToDoListViewHolder.create(parent, itemClickListener)
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    interface OnItemClickListener {
        fun onItemClick(item: ToDoItem)
        fun onSwitchClick(item: ToDoItem, isChecked: Boolean)
        fun onItemLongClick(v: View?, item: ToDoItem)

        fun onButtonInfoClick(item: ToDoItem)
    }


}


class ToDoListViewHolder(itemView: View, private val itemClickListener: ToDoListAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
    private val textView: TextView = itemView.findViewById(R.id.text)
    private val checkBox : MaterialCheckBox = itemView.findViewById(R.id.checkbox)
    private val btnInfo : MaterialButton = itemView.findViewById(R.id.btn_info)
    private lateinit var currentItem: ToDoItem


    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            itemClickListener.onSwitchClick(currentItem, isChecked)
        }
        btnInfo.setOnClickListener {
            itemClickListener.onButtonInfoClick(currentItem)
        }

    }

    fun bind(item: ToDoItem) {
        currentItem = item
        textView.text = item.text
        checkBox.isChecked = item.isDone
        if (checkBox.isChecked) {
            val paint: Paint = textView.paint
            paint.flags = paint.flags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            val paint: Paint = textView.paint
            paint.flags = paint.flags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    companion object {
        fun create(parent: ViewGroup, itemClickListener: ToDoListAdapter.OnItemClickListener): ToDoListViewHolder {
            val binding = ItemTodoListBinding
                .inflate(LayoutInflater.from(parent.context), parent, false);
            val view: View = binding.root
            return ToDoListViewHolder(view, itemClickListener)
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
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }
}


