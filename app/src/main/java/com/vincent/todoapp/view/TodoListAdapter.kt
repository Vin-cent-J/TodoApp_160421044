package com.vincent.todoapp.view

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.vincent.todoapp.databinding.TodoItemLayoutBinding
import com.vincent.todoapp.model.Todo

class TodoListAdapter(val todoList: ArrayList<Todo>, val adapterOnClick:(Todo)->Unit): RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(), TodoCheckedChangeListener, TodoEditClickListener {
    class TodoViewHolder(var bind: TodoItemLayoutBinding): RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var bind= TodoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(bind)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        holder.bind.todo = todoList[position]
        holder.bind.listener = this
        holder.bind.editListener = this
        if (todoList[position].is_done > 0){
            holder.bind.checkTask.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG;
        }



//        holder.bind.checkTask.text = todoList[position].title
//        holder.bind.checkTask.isChecked = false
//        holder.bind.checkTask.setOnCheckedChangeListener{ compoundButton, b ->
//            if(compoundButton.isPressed){
//                adapterOnClick(todoList[position])
//            }
//        }
//        holder.bind.imgBtn.setOnClickListener{
//            val action = TodoListFragmentDirections.actionEditTodoFragment(todoList[position].uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    fun updateTodo(newTodoList: List<Todo>){
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if(cb.isPressed){
            adapterOnClick(obj)
            cb.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG;
        }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionEditTodoFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }


}