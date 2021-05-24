package huji.postpc.ahmadok.todoitemlist

import androidx.recyclerview.widget.DiffUtil

class TodoDiffCallBack : DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.creationTime == newItem.creationTime
    }

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.description == newItem.description && oldItem.isDone == newItem.isDone
    }
}