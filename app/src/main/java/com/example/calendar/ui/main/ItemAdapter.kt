package com.example.calendar.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.model.DateItem
import java.util.*

class ItemAdapter : ListAdapter<DateItem, ItemAdapter.ItemViewHolder>(ItemDiffCallback) {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayLabel = itemView.findViewById<TextView>(R.id.day)
        private val dayOfWeekLable = itemView.findViewById<TextView>(R.id.day_of_week)
        fun bind(item: DateItem) {
            dayLabel.text = item.calendar.get(Calendar.DAY_OF_MONTH).toString()
            dayOfWeekLable.text = item.calendar.get(Calendar.DAY_OF_WEEK).toString()

            if (item.today) {
                itemView.setBackgroundColor(Color.RED)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object ItemDiffCallback : DiffUtil.ItemCallback<DateItem>() {
        override fun areItemsTheSame(oldItem: DateItem, newItem: DateItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DateItem, newItem: DateItem): Boolean {
            return oldItem.calendar.time == newItem.calendar.time
        }
    }
}