package com.example.calendar.ui.main

import android.content.Context
import com.example.calendar.model.DateItem
import java.util.*

class MainThread: Thread() {
    private lateinit var callback: Callback

    interface Callback {
        fun onResult(items: MutableList<DateItem>)
    }

    fun setOnCallback(callback: Callback) {
        this.callback = callback
    }

    override fun run() {
        super.run()

        val calendar = Calendar.getInstance()
        calendar.time = MainViewModel().currentCalendar.time
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val today = Calendar.getInstance()

        // 初週の月曜日
        val beginOfMonth = Calendar.getInstance().apply {
            set(year, month, 1, 0, 0, 0)
            add(Calendar.DAY_OF_MONTH, 1-(get(Calendar.DAY_OF_WEEK)))
        }

        // 最終週の土曜日
        val endOfMonth = Calendar.getInstance().apply {
            set(year, month+1, 0, 0, 0)
            add(Calendar.DAY_OF_MONTH, 7-(get(Calendar.DAY_OF_WEEK)))
        }

        calendar.time = beginOfMonth.time
        val items = mutableListOf<DateItem>()
        while (calendar.timeInMillis <= endOfMonth.timeInMillis) {
            items.add(
                DateItem(calendar.clone() as Calendar,
                (calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)))
            )
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        callback.onResult(items)
    }
}