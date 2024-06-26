package com.vincent.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vincent.todoapp.model.Todo
import com.vincent.todoapp.model.TodoDatabase
import com.vincent.todoapp.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh(){
        loadingLD.value = true
        todoLoadErrorLD.value = false

        launch {
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectAll())
            loadingLD.postValue(false)
        }
    }

    fun clearTask(todo: Todo){
        launch {
            val db = buildDB(getApplication())
            db.todoDao().deleteTodo(todo)
            todoLD.postValue(db.todoDao().selectAll())
        }
    }

    fun checkTodo(todo: Todo){
        launch {
            val db = buildDB(getApplication())
            db.todoDao().isDone(1, todo.uuid)
        }
    }
}