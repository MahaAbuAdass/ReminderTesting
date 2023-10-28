package com.example.remindertestapp.ui.Schedule.new2.myTime


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R

class MyTimeAdapter (
    val scheduleData: List<MeMyScheduleData?>?,
    val itemClicked: (meMyScheduleData: MeMyScheduleData) -> Unit,
    ) : RecyclerView.Adapter<MyTimeAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private val userName: TextView = itemView.findViewById(R.id.tv_name_m)
        private val s_time: TextView = itemView.findViewById(R.id.tv_time_m)
      //  private val s_status: TextView = itemView.findViewById(R.id.tv_status_m)
        private val img_click : ImageView = itemView.findViewById(R.id.img_schedule_m)


        fun setData(meMyScheduleData: MeMyScheduleData?) {
            userName.text = meMyScheduleData?.userName
            s_time.text =meMyScheduleData?.callTime
     //       s_status.text= meMyScheduleData?.scheduleStatus.toString()

            img_click.setOnClickListener{
                meMyScheduleData?.let {
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
            .inflate(R.layout.my_time_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        scheduleData?.get(position)?.let {
            holder.setData(it)
        }
    }


    override fun getItemCount() = scheduleData?.size ?: 0



}