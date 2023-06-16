package com.example.todoapp.ui.adapters

import android.graphics.Paint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.Importance
import com.example.todoapp.data.models.ToDoItem
import com.example.todoapp.databinding.ItemTodoListBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox

class ToDoListAdapter(private val itemClickListener: OnItemClickListener,
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
        fun onSwitchClick(item: ToDoItem, isChecked: Boolean)
        fun onItemLongClick(v: View?, item: ToDoItem)
        fun onButtonInfoClick(item: ToDoItem)
        fun onItemSwipedLeft(item: ToDoItem)
    }


}


class ToDoListViewHolder(
    itemView: View,
    private val itemClickListener: ToDoListAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
    private val text: TextView = itemView.findViewById(R.id.text)
    private val checkBox : MaterialCheckBox = itemView.findViewById(R.id.checkbox)
    private val btnInfo : MaterialButton = itemView.findViewById(R.id.btn_info)
    private val indicator : View = itemView.findViewById(R.id.indicator)
    private val date : TextView = itemView.findViewById(R.id.date)
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
        text.text = item.text
        checkBox.isChecked = item.isDone
        if (item.deadline != null) {
            date.text = item.deadline
            date.visibility = View.VISIBLE
        }
        else date.visibility = View.GONE
        if (item.importance == Importance.HIGH) indicator.setBackgroundResource(R.color.md_theme_light_error)

        if (checkBox.isChecked) {
            val paint: Paint = text.paint
            paint.flags = paint.flags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            val paint: Paint = text.paint
            paint.flags = paint.flags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            itemClickListener: ToDoListAdapter.OnItemClickListener,
        ): ToDoListViewHolder {
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



