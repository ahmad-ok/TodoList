package huji.postpc.ahmadok.todoitemlist

import android.app.Application

class TodoItemApplication : Application() {

    companion object{
        private var instance : TodoItemApplication? = null

        fun getInstance() : TodoItemApplication?{
            return instance
        }
    }
    private var dataBase : TodoItemsHolderImpl? = null

    fun getDataBase() : TodoItemsHolderImpl?{
        return dataBase
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        dataBase = TodoItemsHolderImpl(this)
    }
}
