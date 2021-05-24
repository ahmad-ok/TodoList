package huji.postpc.ahmadok.todoitemlist

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TodoItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val todoItemText: TextView = view.findViewById(R.id.todoItemText)
    val todoItemCheckBox: CheckBox = view.findViewById(R.id.todoItemCheckBox)

}


