package huji.postpc.ahmadok.todoitemlist

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList


class TodoItemsHolderImpl(val context : Context) : TodoItemsHolder {
    override val currentItems: MutableList<TodoItem> = ArrayList()
    private val sp: SharedPreferences = context.getSharedPreferences("local_db_items", Context.MODE_PRIVATE)


    override fun addNewInProgressItem(description: String) {
        val id : String = UUID.randomUUID().toString()
        val item = TodoItem(id, description)
        currentItems.add(0, item)
    }

    override fun markItemDone(item: TodoItem): Int {
        item.isDone = true
        //move item to end
        currentItems.remove(item)
        currentItems.add(item)

        putInSharedPref()

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
        putInSharedPref()
        return addIdx
    }

    override fun deleteItem(item: TodoItem) {
        currentItems.remove(item)
        putInSharedPref()
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

    override fun addAll(holder: List<TodoItem>) {
        currentItems.addAll(holder)
    }

    private fun putInSharedPref(){
        val editor = sp.edit()
        val gson = Gson()
        editor.putString("holder", gson.toJson(currentItems))
        editor.apply()
    }

}