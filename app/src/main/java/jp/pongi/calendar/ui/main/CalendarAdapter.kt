package jp.pongi.calendar.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.pongi.calendar.R
import jp.pongi.calendar.model.DateItem
import java.util.*

class CalendarAdapter : ListAdapter<DateItem, CalendarAdapter.ItemViewHolder>(ItemDiffCallback) {

    lateinit var listener: OnItemClickListener

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayLabel = itemView.findViewById<TextView>(R.id.day)
        private val dayOfWeekLabel = itemView.findViewById<TextView>(R.id.day_of_week)
        fun bind(item: DateItem) {
            dayLabel.text = item.calendar.get(Calendar.DAY_OF_MONTH).toString()
            dayOfWeekLabel.text = item.calendar.get(Calendar.DAY_OF_WEEK).toString()

            if (item.today) {
                itemView.setBackgroundColor(Color.RED)
            } else {
                itemView.setBackgroundColor(0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dateItem = getItem(position)
        holder.bind(dateItem)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(it, dateItem, position)
        }
    }

    object ItemDiffCallback : DiffUtil.ItemCallback<DateItem>() {
        override fun areItemsTheSame(oldItem: DateItem, newItem: DateItem): Boolean {
            return oldItem.calendar.time == newItem.calendar.time
        }

        override fun areContentsTheSame(oldItem: DateItem, newItem: DateItem): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(view: View, item: DateItem, position: Int)
    }
}