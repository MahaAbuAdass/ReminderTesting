package com.example.remindertestapp.ui.Norification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R


class NotificationAdapter (
    val notifications: List<NotificationModel>?,
    private var shareClicked : (notificationModel: NotificationModel) -> Unit
        ) : RecyclerView.Adapter<NotificationAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.tv_title)
        private var date: TextView = itemView.findViewById(R.id.tv_date)
        private var body: TextView = itemView.findViewById(R.id.tv_body)
        private var shareBtn: ImageView = itemView.findViewById(R.id.tv_share)

        fun setData(notificationModel: NotificationModel) {
            title.text = notificationModel.title
            date.text = notificationModel.notificationDate
            body.text = notificationModel.body


            shareBtn.setOnClickListener {
                shareClicked.invoke(notificationModel)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_cell, parent,false)
        return ItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: NotificationAdapter.ItemViewHolder, position: Int) {
        notifications?.get(position)?.let {
            holder.setData(it)
        }
    }

    override fun getItemCount()= notifications?.size ?:0
}