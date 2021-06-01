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

    ) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TodoItem

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
