package com.example.calendar.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.ui.ItemAdapter

class MainFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
    private val viewModel: MainViewModel by activityViewModels()

    val adapter: ItemAdapter = ItemAdapter()


    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    var currentMonth: TextView? = null
    var nextMonth: TextView? = null
    var prevMonth: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.month_name).apply {
            currentMonth = this
        }
        view.findViewById<TextView>(R.id.next).apply {
            nextMonth = this
            setOnClickListener { viewModel.input("next") }
        }
        view.findViewById<TextView>(R.id.prev).apply {
            prevMonth = this
            setOnClickListener { viewModel.input("prev") }
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.monthly_table)
        recyclerView.adapter = adapter

        viewModel.itemList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.inputText.observe(viewLifecycleOwner) {
            currentMonth!!.text = it
            nextMonth!!.text = (it.toInt() + 1).toString()
            prevMonth!!.text = (it.toInt() - 1).toString()
        }
    }
}