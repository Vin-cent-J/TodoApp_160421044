package com.vincent.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.vincent.todoapp.R
import com.vincent.todoapp.databinding.FragmentCreateTodoBinding
import com.vincent.todoapp.model.Todo
import com.vincent.todoapp.viewmodel.DetailTodoViewModel
import com.vincent.todoapp.viewmodel.ListTodoViewModel

class CreateTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var bind:FragmentCreateTodoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        bind.btnSave.setOnClickListener {
            val radio = view.findViewById<RadioButton>(bind.groupPriority.checkedRadioButtonId)
            var todo = Todo(bind.txtTitle.text.toString(), bind.txtNote.text.toString(), radio.tag.toString().toInt(), 0)
            viewModel.addTodo(todo)
            Toast.makeText(it.context, "Todo added", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }
}