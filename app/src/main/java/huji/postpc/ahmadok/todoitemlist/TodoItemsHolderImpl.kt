package huji.postpc.ahmadok.todoitemlist

import android.util.Log


class TodoItemsHolderImpl : TodoItemsHolder {
    override val currentItems: MutableList<TodoItem>  = arrayListOf()


    override fun addNewInProgressItem(description: String) {
        val item = TodoItem(description)
        currentItems.add(0, item)
    }

    override fun markItemDone(item: TodoItem) {
        item.setDone()
        sort()
    }

    override fun markItemInProgress(item: TodoItem) {
        item.setInProgress()
        sort()
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

    override fun getItems(): List<TodoItem> {
        return currentItems
    }

    private fun sort(){
        currentItems.sortWith(compareBy<TodoItem>{!it.isDone()}.thenBy {-it.getCreationTime()})
    }
}