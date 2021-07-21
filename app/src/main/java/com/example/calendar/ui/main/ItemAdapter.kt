package com.example.calendar.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.model.Item

class ItemAdapter : ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback) {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayLabel = itemView.findViewById<TextView>(R.id.day)
        private val dayOfWeekLable = itemView.findViewById<TextView>(R.id.day_of_week)
        fun bind(item: Item) {
            dayLabel.text = item.day.toString()
            dayOfWeekLable.text = item.dayOfWeek.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.date.time == newItem.date.time
        }
    }
}