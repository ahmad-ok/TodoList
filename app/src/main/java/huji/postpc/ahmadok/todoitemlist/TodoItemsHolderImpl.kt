package huji.postpc.ahmadok.todoitemlist


class TodoItemsHolderImpl : TodoItemsHolder {
    override val currentItems: MutableList<TodoItem> = ArrayList()


    override fun addNewInProgressItem(description: String) {
        val item = TodoItem(description)
        currentItems.add(0, item)
    }

    override fun markItemDone(item: TodoItem): Int {
        item.isDone = true
        //move item to end
        currentItems.remove(item)
        currentItems.add(item)

        return currentItems.size - 1
    }

    override fun markItemInProgress(item: TodoItem): Int {
        var addIdx = 0
        for (i in currentItems.indices) {
            addIdx = i
            if (currentItems[i].creationTime < item.creationTime || currentItems[i].isDone) {
                break
            }
        }
        item.isDone = false
        currentItems.remove(item)
        currentItems.add(addIdx, item)
        return addIdx
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


    override fun clear() {
        currentItems.clear()
    }

    override fun addAll(holder: TodoItemsHolder) {
        currentItems.addAll(holder.getItems())
    }

}