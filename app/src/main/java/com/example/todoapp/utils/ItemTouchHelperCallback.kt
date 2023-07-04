package com.example.todoapp.utils

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    private val onItemSwiped: (position: Int) -> Unit,
    private val icon: Drawable?,
    private val background: Drawable
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
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
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )

        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

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
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        onItemSwiped(position)
    }
}
