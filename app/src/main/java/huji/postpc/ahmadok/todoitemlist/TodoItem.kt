package huji.postpc.ahmadok.todoitemlist

import java.io.Serializable

data class TodoItem(
    var description: String = "",
    var isDone: Boolean = false,
    var creationTime: Long = System.currentTimeMillis()
) : Serializable
/*

class TodoItem(var description: String, var isDone: Boolean = false, var creationTime: Int = 0) : Serializable {

//    var isDone = false
//    var creationTime = 0

    companion object{
        var time: Int = 0
    }

    init {
        creationTime = time
        time++

    }


}*/
