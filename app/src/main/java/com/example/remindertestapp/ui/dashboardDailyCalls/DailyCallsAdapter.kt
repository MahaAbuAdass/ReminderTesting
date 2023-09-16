package com.example.remindertestapp.ui.dashboardDailyCalls

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R


class DailyCallsAdapter (
    val allCalls: List<DailyCalls>? ,
    val optionClicked: (allCalls: DailyCalls) -> Unit,
) : RecyclerView.Adapter<DailyCallsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tv_name_d)
        private val time: TextView = itemView.findViewById(R.id.tv_time_d)
        private val topic: TextView = itemView.findViewById(R.id.tv_topic_d)
        private val imgOption : ImageView = itemView.findViewById(R.id.img_option)


        fun setData(allCalls: DailyCalls) {
            userName.text = allCalls.userName
            time.text = allCalls.expectedCallTime
            topic.text=allCalls.callTopic

            imgOption.setOnClickListener {
                optionClicked.invoke(allCalls)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_call_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        allCalls?.get(position)?.let { holder.setData(it) }

    }

    override fun getItemCount() = allCalls?.size ?: 0

}