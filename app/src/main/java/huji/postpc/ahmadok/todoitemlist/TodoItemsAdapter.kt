package huji.postpc.ahmadok.todoitemlist

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.lang.IllegalStateException
import java.util.*


class TodoItemsAdapter(private val context: Context) :
    ListAdapter<TodoItem, TodoItemViewHolder>(TodoDiffCallBack()) {
    private val itemHolder = TodoItemApplication.getInstance()?.getDataBase()
    var recyclerView: RecyclerView? = null

    private val activityLauncher = (context as ComponentActivity).registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val data: Intent? = it.data
            val item = Gson().fromJson(data?.getStringExtra("item"), TodoItem::class.java)
            val changed = data?.getBooleanExtra("done_changed", false)
            if (changed == true ) {
                val listItem = itemHolder?.currentItems?.find { k -> k == item }
                listItem!!
                if (item.isDone) {
                    itemHolder?.markItemDone(listItem)
                } else {
                    itemHolder?.markItemInProgress(listItem)
                }
                notifyDataSetChanged()

            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.row_todo_item, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TodoItemViewHolder, position: Int) {
        val item = getItem(position)

        viewHolder.todoItemText.text = item.description
        viewHolder.todoItemCheckBox.isChecked = item.isDone

        if (item.isDone) {
            viewHolder.todoItemText.paintFlags =
                (viewHolder.todoItemText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            viewHolder.todoItemText.setTextColor(Color.GRAY)
        } else {

            viewHolder.todoItemText.paintFlags =
                (viewHolder.todoItemText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
            viewHolder.todoItemText.setTextColor(Color.BLACK)

        }


        viewHolder.itemView.setOnClickListener {
            val modifyIntent = Intent(context, EditActivity::class.java)
            val gson = Gson()
            val itemAsJson = gson.toJson(item)
            modifyIntent.putExtra("item", itemAsJson)
//            context.startActivity(modifyIntent)
            activityLauncher.launch(modifyIntent)
        }

        viewHolder.todoItemCheckBox.setOnCheckedChangeListener { button, isChecked ->
            if (!button.isPressed) {
                return@setOnCheckedChangeListener
            }
            val newIdx: Int
            if (isChecked) {
                newIdx = itemHolder?.markItemDone(item)!!
                viewHolder.todoItemText.paintFlags =
                    (viewHolder.todoItemText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                viewHolder.todoItemText.setTextColor(Color.GRAY)
            } else {
                newIdx = itemHolder?.markItemInProgress(item)!!

                viewHolder.todoItemText.paintFlags =
                    (viewHolder.todoItemText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                viewHolder.todoItemText.setTextColor(Color.BLACK)

            }
            notifyItemMoved(viewHolder.adapterPosition, newIdx)

        }
    }
}
