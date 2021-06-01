package huji.postpc.ahmadok.todoitemlist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.util.*
import java.util.concurrent.TimeUnit

class EditActivity : AppCompatActivity() {

    lateinit var item: TodoItem
    private var originDone: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_screen)
        val holder = TodoItemApplication.getInstance()?.getDataBase()

        val gson = Gson()
        item = gson.fromJson(intent.getStringExtra("item"), TodoItem::class.java)
        originDone = item.isDone
        val createDate = item.creationDate
        val modifyDate = item.modifiedDate
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val lastModifiedTextView: TextView = findViewById(R.id.lastModifiedTextView)
        val dateCreatedTextView: TextView = findViewById(R.id.dateCreatedTextView)
        val statusCheckBox: CheckBox = findViewById(R.id.statusCheckBox)
        statusCheckBox.isChecked = item.isDone
        val intent = Intent("data_modified")

        descriptionEditText.setText(item.description)
        lastModifiedTextView.text = getLastModified(modifyDate, createDate)
        dateCreatedTextView.text = getString(R.string.date_created, createDate.time)

        descriptionEditText.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                holder?.editTodoItem(item.id, s.toString(), Calendar.getInstance())
                sendBroadcast(intent)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        statusCheckBox.setOnCheckedChangeListener {_, isChecked ->
            if (isChecked) {
                holder?.markItemDone(item)
            } else {
                holder?.markItemInProgress(item)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        val gson = Gson()
        intent.putExtra("item", gson.toJson(item))
        intent.putExtra("done_changed", item.isDone != originDone)
        setResult(RESULT_OK, intent)
        finish()
//        super.onBackPressed()

    }

    private fun getLastModified(modifiedCalendar: Calendar, creationCalendar: Calendar): String {
        val currTime = Calendar.getInstance().timeInMillis
        val oldTime = modifiedCalendar.timeInMillis
        val timePassed = currTime - oldTime

        val str: String = when {
            creationCalendar == modifiedCalendar -> {
                "Not Yet Modified"
            }
            TimeUnit.MILLISECONDS.toSeconds(timePassed) in 1..59 ->{
                "${TimeUnit.MILLISECONDS.toSeconds(timePassed)} Seconds Ago"
            }
            TimeUnit.MILLISECONDS.toMinutes(timePassed) in 1..59 -> {
                "${TimeUnit.MILLISECONDS.toMinutes(timePassed)} Minutes Ago"
            }
            TimeUnit.MILLISECONDS.toHours(timePassed) in 1..23 -> {
                "${TimeUnit.MILLISECONDS.toHours(timePassed)} Hours Ago"
            }
            else -> {
                modifiedCalendar.time.toString()
            }
        }

        return "Last Modified: $str"

    }

}
