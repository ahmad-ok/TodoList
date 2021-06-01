package huji.postpc.ahmadok.todoitemlist

import android.content.*
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    var holder: TodoItemsHolder? = null
    val adapter: TodoItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (holder == null) {
            holder = TodoItemApplication.getInstance()?.getDataBase()
        }

        val sp: SharedPreferences? = getSharedPreferences("local_db_items", MODE_PRIVATE)
        val adapter = TodoItemsAdapter(this)
        val todoItemRecycler: RecyclerView = findViewById(R.id.recyclerTodoItemsList)
        todoItemRecycler.adapter = adapter
        todoItemRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val itemsType = object : TypeToken<ArrayList<TodoItem>>() {}.type
        val gson = Gson()
        val prev = gson.fromJson<ArrayList<TodoItem>>(sp?.getString("holder", "[]"), itemsType)
        if (prev != null) {
            holder?.clear()
            holder?.addAll(prev)

        }
//        holder?.currentItems?.sortedWith { item1: TodoItem, item2: TodoItem ->
//            0
//        }
        holder?.currentItems?.sortedWith { todoItem: TodoItem, todoItem1: TodoItem ->
            val doneness = todoItem.isDone.compareTo(todoItem1.isDone)
            if (doneness == 0) {
                return@sortedWith todoItem.creationTime.compareTo(todoItem1.creationTime)
            } else {
                return@sortedWith doneness
            }
        }

        adapter.submitList(holder?.currentItems)
        adapter.notifyDataSetChanged()

        val broadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                if (intent?.action == "data_modified") {
                    adapter.submitList(holder?.currentItems)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        val addTodoButton: FloatingActionButton = findViewById(R.id.buttonCreateTodoItem)
        val addTaskText: EditText = findViewById(R.id.editTextInsertTask)

        addTodoButton.setOnClickListener {

            if (addTaskText.text.toString() != "") {
                val text: String = addTaskText.text.toString()

                holder?.addNewInProgressItem(text)
                adapter.submitList(holder?.currentItems)
                adapter.notifyItemInserted(0)
                addTaskText.setText("")
            }
        }

        addTaskText.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handled = true
                addTodoButton.callOnClick()
            }

            handled
        }
        Toast.makeText(this, "Swipe left or right to delete!", Toast.LENGTH_LONG).show()
        setDeleteOnSwipe(holder, adapter, todoItemRecycler)
        registerReceiver(broadCastReceiver, IntentFilter("data_modified"))

    }

    private fun setDeleteOnSwipe(
        holder: TodoItemsHolder?, adapter: TodoItemsAdapter,
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
                holder?.deleteItem(holder.getItem(position))
                adapter.submitList(holder?.currentItems)
                adapter.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(todoItemRecycler)
    }


}
