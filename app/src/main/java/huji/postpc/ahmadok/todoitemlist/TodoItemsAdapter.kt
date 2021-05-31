package huji.postpc.ahmadok.todoitemlist

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter


class TodoItemsAdapter() : ListAdapter<TodoItem, TodoItemViewHolder>(TodoDiffCallBack()) {
    val itemHolder = TodoItemApplication.getInstance()?.getDataBase()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.row_todo_item, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.todoItemText.text = item.description
        holder.todoItemCheckBox.isChecked = item.isDone

        if (item.isDone) {
            holder.todoItemText.paintFlags =
                (holder.todoItemText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            holder.todoItemText.setTextColor(Color.GRAY)
        } else {

            holder.todoItemText.paintFlags =
                (holder.todoItemText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
            holder.todoItemText.setTextColor(Color.BLACK)

        }
        holder.todoItemCheckBox.setOnCheckedChangeListener { _, isChecked ->
            val newIdx: Int
            if (isChecked) {
                newIdx = itemHolder?.markItemDone(item)!!
                holder.todoItemText.paintFlags =
                    (holder.todoItemText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                holder.todoItemText.setTextColor(Color.GRAY)
                notifyItemMoved(holder.adapterPosition, newIdx)
            } else {
                newIdx = itemHolder?.markItemInProgress(item)!!

                holder.todoItemText.paintFlags =
                    (holder.todoItemText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                holder.todoItemText.setTextColor(Color.BLACK)

                notifyItemMoved(holder.adapterPosition, newIdx)
            }
        }

    }

}
