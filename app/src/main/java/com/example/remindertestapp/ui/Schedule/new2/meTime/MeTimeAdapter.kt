package com.example.remindertestapp.ui.Schedule.new2.meTime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R

class MeTimeAdapter (
    val Data: List<InformationReceiverResponseModel?>?,
    val itemClicked: (informationReceiverResponseModel: InformationReceiverResponseModel) -> Unit,
) : RecyclerView.Adapter<MeTimeAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tv_name_m)
        private val s_time: TextView = itemView.findViewById(R.id.tv_time_m)
        private val img_click : ImageView = itemView.findViewById(R.id.img_schedule_m)


        fun setData(informationReceiverResponseModel: InformationReceiverResponseModel?) {
            userName.text = informationReceiverResponseModel?.userName
            s_time.text =informationReceiverResponseModel?.callTime

            img_click.setOnClickListener{
                informationReceiverResponseModel?.let {
                        it1 -> itemClicked.invoke(it1)
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.me_time_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Data?.get(position)?.let {
            holder.setData(it)
        }    }

    override fun getItemCount() = Data?.size ?: 0

}