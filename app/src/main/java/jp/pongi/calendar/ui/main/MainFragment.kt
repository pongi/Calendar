package jp.pongi.calendar.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import jp.pongi.calendar.databinding.MainFragmentBinding
import jp.pongi.calendar.model.DateItem

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var calendarAdapter: CalendarAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@MainFragment.viewLifecycleOwner
            viewModel = mainViewModel
            calendarLayout.monthlyTable.apply {
                calendarAdapter = CalendarAdapter()
                calendarAdapter.listener = object : CalendarAdapter.OnItemClickListener {
                    override fun onItemClicked(view: View, item: DateItem, position: Int) {
                        val action = MainFragmentDirections.actionMainToPlan(item)
                        view.findNavController().navigate(action)
                    }
                }
                adapter = calendarAdapter
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.itemList.observe(viewLifecycleOwner) {
            calendarAdapter.submitList(it)
        }
    }
}