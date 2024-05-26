package com.vincent.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo:Todo)

    @Query("SELECT * from todo order by priority desc")
    fun selectAll():List<Todo>

    @Query("SELECT * FROM TODO where uuid=:id")
    fun selectTodo(id:Int):Todo

    @Delete
    fun deleteTodo(todo:Todo)

    @Query("update todo set title=:title, notes=:notes, priority=:priority where uuid=:id")
    fun update(title:String, notes:String, priority:Int, id:Int)

    @Update
    fun updateTodo(todo: Todo)

    @Query("update todo set is_done=:done where uuid=:id")
    fun isDone(done:Int, id:Int)
}