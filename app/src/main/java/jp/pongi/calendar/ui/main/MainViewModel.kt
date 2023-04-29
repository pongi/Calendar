package jp.pongi.calendar.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.pongi.calendar.model.DateItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {

    private val _currentMonth = MutableLiveData<String>()
    val currentMonth: LiveData<String> = _currentMonth

    private val _itemList = MutableLiveData<List<DateItem>>()
    val itemList: LiveData<List<DateItem>> = _itemList

    private val today = Calendar.getInstance()
    private val currentCalendar = Calendar.getInstance()

    init {
        getCalendar(currentCalendar)
    }

    private fun getCalendar(calendar: Calendar) {
        viewModelScope.launch(Dispatchers.IO) {
            retrieveCalendar(calendar)
        }
    }

    private fun retrieveCalendar(current: Calendar) {
        val year = current.get(Calendar.YEAR)
        val month = current.get(Calendar.MONTH)

        // 初週の月曜日
        val beginOfMonth = (current.clone() as Calendar).apply {
            set(year, month, 1)
            add(Calendar.DAY_OF_MONTH, 1 - get(Calendar.DAY_OF_WEEK))
        }

        // 最終週の土曜日+1週
        val endOfMonth = (current.clone() as Calendar).apply {
            set(year, month + 1, 0)
            add(Calendar.DAY_OF_MONTH, 7 - get(Calendar.DAY_OF_WEEK))
        }

        val tmp = beginOfMonth.clone() as Calendar
        val items = mutableListOf<DateItem>()
        while (tmp.timeInMillis <= endOfMonth.timeInMillis) {
            items.add(DateItem(tmp.clone() as Calendar, tmp.time == today.time))
            tmp.add(Calendar.DAY_OF_MONTH, 1)
        }
        _itemList.postValue(items)
        _currentMonth.postValue((current.get(Calendar.MONTH) + 1).toString())
    }

    fun moveToNextMonth() {
        moveToMonth("next")
    }

    fun moveToPrevMonth() {
        moveToMonth("prev")
    }

    fun moveToMonth(text: String) {
        if (text == "next") {
            currentCalendar.add(Calendar.MONTH, +1)
        }
        if (text == "prev") {
            currentCalendar.add(Calendar.MONTH, -1)
        }
        getCalendar(currentCalendar)
    }
}