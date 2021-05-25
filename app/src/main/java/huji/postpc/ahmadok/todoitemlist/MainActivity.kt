package huji.postpc.ahmadok.todoitemlist

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    var items: MutableList<TodoItem> = ArrayList()
    var holder: TodoItemsHolder = TodoItemsHolderImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = TodoItemsAdapter(holder)
        val todoItemRecycler: RecyclerView = findViewById(R.id.recyclerTodoItemsList)
        todoItemRecycler.adapter = adapter
        todoItemRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val addTodoButton: FloatingActionButton = findViewById(R.id.buttonCreateTodoItem)
        val addTaskText: EditText = findViewById(R.id.editTextInsertTask)

        addTodoButton.setOnClickListener {

            if (addTaskText.text.toString() != "") {
                val text: String = addTaskText.text.toString()

                holder.addNewInProgressItem(text)
                adapter.submitList(holder.currentItems)
                adapter.notifyItemInserted(0)
                addTaskText.setText("")
            }
        }
        Toast.makeText(this, "Swipe left or right to delete!", Toast.LENGTH_LONG).show()
        setDeleteOnSwipe(holder, adapter, todoItemRecycler)

    }

    private fun setDeleteOnSwipe(
        holder: TodoItemsHolder, adapter: TodoItemsAdapter,
        todoItemRecycler: RecyclerView
    ) {
        val itemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

            ) {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.adapterPosition
                holder.deleteItem(holder.getItem(position))
                adapter.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(todoItemRecycler)
    }
}
