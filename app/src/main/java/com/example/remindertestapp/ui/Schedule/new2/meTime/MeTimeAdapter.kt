package com.example.remindertestapp.ui.Schedule.new2.meTime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R
import com.example.remindertestapp.enums.OrderStatusEnum

class MeTimeAdapter (
    val data: List<InformationReceiverResponseModel?>?,
    val itemClicked2: (informationReceiverResponseModel: InformationReceiverResponseModel) -> Unit,
) : RecyclerView.Adapter<MeTimeAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tv_name_m)
        private val s_time: TextView = itemView.findViewById(R.id.tv_time_m)
        private val img_click2 : ImageView = itemView.findViewById(R.id.img_schedule_m2)
        private val status : TextView = itemView.findViewById(R.id.tv_status_m)


        fun setData(informationReceiverResponseModel: InformationReceiverResponseModel?) {
            userName.text = informationReceiverResponseModel?.userName
            s_time.text =informationReceiverResponseModel?.callTime
            status.text = when (informationReceiverResponseModel?.scheduleStatus){
                OrderStatusEnum.PENDING.status -> "Pending"
                OrderStatusEnum.ACCEPTED.status -> "Accept"
                OrderStatusEnum.CANCELED.status ->"Cancell"
                else ->""
            }


            img_click2.setOnClickListener{
                informationReceiverResponseModel?.let {
                        it2 -> itemClicked2.invoke(it2)
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
        data?.get(position)?.let {
            holder.setData(it)
        }    }

    override fun getItemCount() = data?.size ?: 0

}