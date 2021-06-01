package huji.postpc.ahmadok.todoitemlist

import java.io.Serializable
import java.util.*

data class TodoItem(
    var id : String = "",
    var description: String = "",
    var isDone: Boolean = false,
    var creationTime: Long = System.currentTimeMillis(),
    var creationDate: Calendar = Calendar.getInstance(),
    var modifiedDate: Calendar = Calendar.getInstance()

    ) : Serializable
