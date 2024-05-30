package com.vincent.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.vincent.todoapp.R
import com.vincent.todoapp.databinding.FragmentCreateTodoBinding
import com.vincent.todoapp.databinding.FragmentEditTodoBinding
import com.vincent.todoapp.model.Todo
import com.vincent.todoapp.viewmodel.DetailTodoViewModel


class EditTodoFragment : Fragment(), RadioClickListener, TodoEditClickListener {

    private lateinit var bind: FragmentEditTodoBinding
    private lateinit var viewModel: DetailTodoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentEditTodoBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        bind.txtJudul.text = "Edit Todo"
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)

//        bind.btnSave.setOnClickListener {
//            val radio = view.findViewById<RadioButton>(bind.groupPriority.checkedRadioButtonId)
//            val priority = radio.tag.toString().toInt()
//            viewModel.update(bind.txtTitle.text.toString(), bind.txtNote.text.toString(), priority, uuid)
//            Navigation.findNavController(it).popBackStack()
//        }
        bind.radiolistener = this
        bind.savelistener = this
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer{
//            bind.txtTitle.setText(it.title)
//            bind.txtNote.setText(it.notes)
//            when(it.priority){
//                1->bind.radioLow.isChecked = true
//                2->bind.radioMedium.isChecked = true
//                else->bind.radioHigh.isChecked = true
//            }
            bind.todo = it
        })
    }

    override fun onRadioClick(v: View) {
        bind.todo!!.priority= v.tag.toString().toInt()
    }

    override fun onTodoEditClick(v: View) {
        viewModel.updateTodo(bind.todo!!)
        Toast.makeText(context, "Todo updated", Toast.LENGTH_SHORT).show()

    }
}