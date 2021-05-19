package huji.postpc.ahmadok.todoitemlist

// TODO: feel free to add/change/remove methods as you want
interface TodoItemsHolder {
    /** Get a copy of the current items list  */
    val currentItems: List<TodoItem>

    /**
     * Creates a new TodoItem and adds it to the list, with the @param description and status=IN-PROGRESS
     * Subsequent calls to [getCurrentItems()] should have this new TodoItem in the list
     */
    fun addNewInProgressItem(description: String)

    /** mark the @param item as DONE  */
    fun markItemDone(item: TodoItem)

    /** mark the @param item as IN-PROGRESS  */
    fun markItemInProgress(item: TodoItem)

    /** delete the @param item  */
    fun deleteItem(item: TodoItem)

    /** get i'th item from item holder*/
    fun getItem(position: Int) : TodoItem

    /** get number of items in holder*/
    fun getSize() : Int
}