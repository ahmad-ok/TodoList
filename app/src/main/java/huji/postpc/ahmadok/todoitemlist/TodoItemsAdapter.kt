package huji.postpc.ahmadok.todoitemlist

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class TodoItemsAdapter : RecyclerView.Adapter<TodoItemViewHolder>() {

    var todoItems : TodoItemsHolder? = null

    fun setItems(todoItems : TodoItemsHolder){
        this.todoItems = todoItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.row_todo_item, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val item = todoItems?.getItem(position)
        if (item != null) {
            holder.todoItemText.setText(item.getDesc())
            holder.todoItemCheckBox.setChecked(item.isDone())

            holder.todoItemCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    item.setDone()
                    // add strikethrough to text
                    strikeThroughText(holder)
                } else {
                    item.setInProgress()
                    // remove strikethrough
                    unstrikeThroughText(holder)
                }

            }
        }
    }

    private fun unstrikeThroughText(holder: TodoItemViewHolder) {
        holder.todoItemText.setPaintFlags(holder.todoItemText.getPaintFlags()
                    and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        )
    }

    private fun strikeThroughText(holder: TodoItemViewHolder) {
        holder.todoItemText.setPaintFlags(holder.todoItemText.getPaintFlags()
                    or Paint.STRIKE_THRU_TEXT_FLAG)
    }

    override fun getItemCount(): Int {
        val todoItems1 = todoItems
        return if (todoItems1 != null) todoItems1.getSize() else 0
    }
}