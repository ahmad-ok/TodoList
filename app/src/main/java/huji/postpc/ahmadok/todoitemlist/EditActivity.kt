package huji.postpc.ahmadok.todoitemlist

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import java.util.concurrent.TimeUnit

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_screen)
        val description = intent.getStringExtra("description")
        val creationCalendar : Calendar = intent.getSerializableExtra("creationDate") as Calendar
        val modifiedCalendar : Calendar = intent.getSerializableExtra("modifiedDate") as Calendar
        val status = intent.getBooleanExtra("status", false)

        val createDate = creationCalendar.time
        val modifyDate = modifiedCalendar.time
        val descriptionEditActivity : EditText = findViewById(R.id.descriptionEditText)
        val lastModifiedTextView : TextView = findViewById(R.id.lastModifiedTextView)
        val dateCreatedTextView : TextView = findViewById(R.id.dateCreatedTextView)
        val statusCheckBox : CheckBox = findViewById(R.id.statusCheckBox)

        descriptionEditActivity.setText(description)
        lastModifiedTextView.text = getLastModified(modifiedCalendar, creationCalendar)
        dateCreatedTextView.text = getString(R.string.date_created, createDate)
        statusCheckBox.isChecked = status
    }

    private fun getLastModified(modifiedCalendar: Calendar, creationCalendar: Calendar): String{
        val currTime = Calendar.getInstance().timeInMillis
        val oldTime = modifiedCalendar.timeInMillis
        val timePassed = currTime - oldTime

        val str : String = when {
            creationCalendar == modifiedCalendar -> {
                "Not Yet Modified"
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
