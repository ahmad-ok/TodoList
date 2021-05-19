package huji.postpc.ahmadok.todoitemlist

import android.util.Log


class TodoItemsHolderImpl : TodoItemsHolder {
    override val currentItems: MutableList<TodoItem>  = arrayListOf()

    override fun addNewInProgressItem(description: String) {
        val item = TodoItem(description)
        currentItems.add(0, item)
        Log.i("aaaaaaaaaa", "addNewInProgressItem: item in first idx is ${item.getDesc()}")
    }

    override fun markItemDone(item: TodoItem) {
        item.setDone()
    }

    override fun markItemInProgress(item: TodoItem) {
        item.setInProgress()
    }

    override fun deleteItem(item: TodoItem) {
        currentItems.remove(item)
    }

    override fun getItem(position: Int): TodoItem {
        return currentItems[position]
    }

    override fun getSize(): Int {
        return currentItems.size
    }
}