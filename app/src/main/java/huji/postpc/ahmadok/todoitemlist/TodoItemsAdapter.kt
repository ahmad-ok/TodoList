package huji.postpc.ahmadok.todoitemlist

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ListAdapter
import java.util.*


class TodoItemsAdapter(private val context: Context) : ListAdapter<TodoItem, TodoItemViewHolder>(TodoDiffCallBack()) {
    val itemHolder = TodoItemApplication.getInstance()?.getDataBase()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.row_todo_item, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TodoItemViewHolder, position: Int) {
        val item = getItem(position)

        viewHolder.todoItemText.text = item.description
        viewHolder.todoItemCheckBox.isChecked = item.isDone

        if (item.isDone) {
            viewHolder.todoItemText.paintFlags =
                (viewHolder.todoItemText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            viewHolder.todoItemText.setTextColor(Color.GRAY)
        } else {

            viewHolder.todoItemText.paintFlags =
                (viewHolder.todoItemText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
            viewHolder.todoItemText.setTextColor(Color.BLACK)

        }
        viewHolder.todoItemCheckBox.setOnCheckedChangeListener { _, isChecked ->
            val newIdx: Int
            if (isChecked) {
                newIdx = itemHolder?.markItemDone(item)!!
                viewHolder.todoItemText.paintFlags =
                    (viewHolder.todoItemText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                viewHolder.todoItemText.setTextColor(Color.GRAY)
                notifyItemMoved(viewHolder.adapterPosition, newIdx)
            } else {
                newIdx = itemHolder?.markItemInProgress(item)!!

                viewHolder.todoItemText.paintFlags =
                    (viewHolder.todoItemText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                viewHolder.todoItemText.setTextColor(Color.BLACK)

                notifyItemMoved(viewHolder.adapterPosition, newIdx)
            }
        }

        viewHolder.itemView.setOnClickListener {
            val modifyIntent = Intent(context, EditActivity::class.java)
            modifyIntent.putExtra("description",item.description)
            modifyIntent.putExtra("creationDate",item.creationDate)
            modifyIntent.putExtra("modifiedDate",item.modifiedDate)
            modifyIntent.putExtra("status",item.isDone)
            context.startActivity(modifyIntent)
        }

    }

}
