package com.example.calendar.ui.main

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.calendar.R
import java.text.SimpleDateFormat
import java.util.*

class PlanFragment : Fragment(R.layout.plan_fragment) {

    private val args: PlanFragmentArgs by navArgs()
    private val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN)

    lateinit var startDateEdit: EditText
    lateinit var endDateEdit: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 開始日
        val start = args.item.calendar.clone() as Calendar
        start.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR) + 1)
        startDateEdit = view.findViewById(R.id.start_date)
        startDateEdit.setText(formatter.format(start.time))

        // 終了日
        val end = start.clone() as Calendar
        end.add(Calendar.HOUR_OF_DAY, 1)
        endDateEdit = view.findViewById(R.id.end_date)
        endDateEdit.setText(formatter.format(end.time))
    }
}