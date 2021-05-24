package huji.postpc.ahmadok.todoitemlist

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter


class TodoItemsAdapter(
    val itemHolder: TodoItemsHolder
) : ListAdapter<TodoItem, TodoItemViewHolder>(TodoDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.row_todo_item, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.todoItemText.setText(item.description)
        holder.todoItemCheckBox.isChecked = item.isDone
        holder.todoItemCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val new_idx = itemHolder.markItemDone(item)
                Log.i("Adapter", "onBindViewHolder: check new index is $new_idx")
                holder.todoItemText.paintFlags =
                    (holder.todoItemText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)

                notifyItemMoved(holder.adapterPosition, new_idx)
            } else {
                val new_idx = itemHolder.markItemInProgress(item)
                Log.i("Adapter", "onBindViewHolder: uncheck new index is $new_idx")
                holder.todoItemText.paintFlags =
                    (holder.todoItemText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                notifyItemMoved(holder.adapterPosition, new_idx)
            }
        }

    }

}
