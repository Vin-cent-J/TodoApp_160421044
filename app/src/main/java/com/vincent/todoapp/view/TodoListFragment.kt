package com.vincent.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincent.todoapp.R
import com.vincent.todoapp.databinding.FragmentTodoListBinding
import com.vincent.todoapp.viewmodel.ListTodoViewModel

class TodoListFragment : Fragment() {

    private lateinit var bind: FragmentTodoListBinding
    private lateinit var viewModel: ListTodoViewModel
    private val adapter = TodoListAdapter(arrayListOf(), {item-> viewModel.clearTask(item)})
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bind = FragmentTodoListBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()
        bind.recViewTodo.layoutManager = LinearLayoutManager(context)
        bind.recViewTodo.adapter = adapter

        bind.btnFab.setOnClickListener{
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            adapter.updateTodo(it)
            if(it.isEmpty()){
                bind.recViewTodo.visibility = View.GONE
                bind.txtError.text = "Todo is empty"
            }
            else{
                bind.recViewTodo.visibility = View.VISIBLE
                bind.txtError.visibility = View.GONE
            }
            viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
                if(it == false){
                    bind.progressLoad.visibility = View.GONE
                }
            })
            viewModel.todoLoadErrorLD.observe(viewLifecycleOwner, Observer {
                if(it == false){
                    bind.txtError.visibility = View.GONE
                }
            })
        })
    }
}