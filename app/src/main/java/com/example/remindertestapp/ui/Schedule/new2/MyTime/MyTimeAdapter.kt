//package com.example.remindertestapp.ui.Schedule.new2.MyTime
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.remindertestapp.R
//import com.example.remindertestapp.ui.homeContact.contacts.PhoneNumbersResponse
//import com.example.remindertestapp.ui.homeContact.invite.NotExistingUserAdapter
//
//class MyTimeAdapter (
//    val scheduleData: List<MeMyScheduleData?>?,
//) : RecyclerView.Adapter<NotExistingUserAdapter.ItemViewHolder>() {
//
//    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//      //  private val firstName: TextView = itemView.findViewById(R.id.tv_i_first_name)
//
//
//
//        fun setData(meMyScheduleData: MeMyScheduleData?) {
//
//        }
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ItemViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.me_time_cell, parent, false)
//        return ItemViewHolder(view)
//    }
//
//
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        holder.setData(scheduleData?.get(position))
//    }
//
//    override fun getItemCount() = scheduleData?.size ?: 0
//
//}