package com.example.calendar.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.model.DateItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel(), MainThread.Callback {

    private val _inputText = MutableLiveData<String>()
    val inputText: LiveData<String> = _inputText

    private val _itemList = MutableLiveData<List<DateItem>>()
    val itemList: LiveData<List<DateItem>> = _itemList

    val currentCalendar = Calendar.getInstance()

//    private val mainThread = MainThread()
    private val TAG = "MainViewModel"

    init {
//        mainThread.setOnCallback(this)
//        mainThread.start()

        retrieveCalendar()

    }

    override fun onResult(items: MutableList<DateItem>) {
        _inputText.postValue((currentCalendar.get(Calendar.MONTH)+1).toString())
        _itemList.postValue(items)
    }

    fun retrieveCalendar() {
        Log.d(TAG, "start retrieveCalendar")
        //別スレッド処理する
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "start launch")

            val calendar = Calendar.getInstance()
            calendar.time = currentCalendar.time
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
                items.add(DateItem(calendar.clone() as Calendar,
                    (calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))))
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            _inputText.postValue((currentCalendar.get(Calendar.MONTH)+1).toString())
            _itemList.postValue(items)

            Log.d(TAG, "finish launch")

            aaaa()
        }

        Log.d(TAG, "finish retrieveCalendar")
    }

    suspend fun aaaa() {

    }


    fun input(text: String) {
        if (text == "next") {
            currentCalendar.add(Calendar.MONTH, +1)
        }
        if (text == "prev") {
            currentCalendar.add(Calendar.MONTH, -1)
        }
        retrieveCalendar()
    }
}