package com.example.calendar.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.model.DateItem

class MainFragment : Fragment(R.layout.main_fragment) {

    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }


    lateinit var currentMonth: TextView
    lateinit var nextMonth: TextView
    lateinit var prevMonth: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentMonth = view.findViewById(R.id.month_name)
        nextMonth = view.findViewById<TextView>(R.id.next)
        prevMonth = view.findViewById(R.id.prev)

        nextMonth.setOnClickListener { viewModel.input("next") }
        prevMonth.setOnClickListener { viewModel.input("prev") }

        val adapter = DateAdapter()
        adapter.listener = object : DateAdapter.OnItemClickListener {
            override fun onItemClicked(view: View, item: DateItem, position: Int) {
                val action = MainFragmentDirections.actionMainToPlan(item)
                view.findNavController().navigate(action)
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.monthly_table)
        recyclerView.adapter = adapter
        viewModel.itemList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.inputText.observe(viewLifecycleOwner) {
            currentMonth.text = it
            nextMonth.text = (it.toInt() + 1).toString()
            prevMonth.text = (it.toInt() - 1).toString()
        }
    }
}