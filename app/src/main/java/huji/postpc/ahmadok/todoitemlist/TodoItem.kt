package huji.postpc.ahmadok.todoitemlist

import java.io.Serializable

class TodoItem(description : String) : Serializable {

    private val _description : String = description
    private var _isDone = false
    private var _creationTime = 0

    companion object{
        var time: Int = 0
    }

    init {
        _creationTime = time
        time++

    }

    fun setDone(){
        _isDone = true
    }

    fun setInProgress(){
        _isDone = false
    }

    fun isDone() : Boolean{
        return _isDone
    }

    fun getDesc() : String{
        return _description
    }

    fun getCreationTime() : Int{
        return _creationTime
    }

}