package huji.postpc.ahmadok.todoitemlist

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val holder: TodoItemsHolder = TodoItemsHolderImpl()

        val adapter = TodoItemsAdapter()
        val todoItemRecycler : RecyclerView = findViewById(R.id.recyclerTodoItemsList)
        todoItemRecycler.adapter = adapter
        todoItemRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val addTodoButton : FloatingActionButton = findViewById(R.id.buttonCreateTodoItem)
        val addTaskText : EditText = findViewById(R.id.editTextInsertTask)

        addTodoButton.setOnClickListener{

            if(addTaskText.getText().toString() != ""){
                val text : String = addTaskText.getText().toString()

                holder.addNewInProgressItem(text)
                adapter.setItems(holder)
                adapter.notifyItemInserted(0)
                addTaskText.setText("")
            }
        }
        Toast.makeText(this, "Swipe left or right to delete!", Toast.LENGTH_LONG).show()
        setDeleteOnSwipe(holder, adapter, todoItemRecycler)

    }

    private fun setDeleteOnSwipe(holder: TodoItemsHolder, adapter: TodoItemsAdapter,
                                 todoItemRecycler: RecyclerView) {
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


/*

SPECS:

a- the "TodoItems" list is shown in the screen
  * the order of the TodoItems in the UI is:
    - all IN-PROGRESS items are shown first. items are sorted by creation time,
      where the last-created item is the first item in the list
    - all DONE items are shown afterwards, no particular sort is needed (but try to think about what's the best UX for the user)
- when a screen rotation happens (user flips the screen):
  * the UI should still show the same list of TodoItems
  * the edit-text should store the same user-input (don't erase input upon screen change)

*/
