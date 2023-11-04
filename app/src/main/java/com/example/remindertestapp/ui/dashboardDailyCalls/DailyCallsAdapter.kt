package com.example.remindertestapp.ui.dashboardDailyCalls

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R
import com.example.remindertestapp.ui.Norification.NotificationModel


class DailyCallsAdapter (
    val allCalls: List<DailyCalls>? ,
    val optionClicked: (allCalls: DailyCalls) -> Unit,
    val cancelClicked :(allCalls: DailyCalls ) -> Unit,
    val reScheduleClicked :(allCalls: DailyCalls ) -> Unit,
    val userCall : (allCalls: DailyCalls) -> Unit



) : RecyclerView.Adapter<DailyCallsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tv_name_d)
        private val time: TextView = itemView.findViewById(R.id.tv_time_d)

        private val cancelBtn : Button = itemView.findViewById(R.id.btn_reject_dc)
        private val reScheduleBtn: Button = itemView.findViewById(R.id.btn_reSchedule_dc)
        private var callUser : ImageView = itemView.findViewById(R.id.img_call)


        fun setData(allCalls: DailyCalls) {
            userName.text = allCalls.userName
           val timeD = allCalls.callTime

            val dateParts = timeD?.split("T")
           time.text = dateParts?.get(1)


            callUser.setOnClickListener {
                userCall.invoke(allCalls)
            }


            cancelBtn.setOnClickListener {
                cancelClicked.invoke(allCalls)
            }

            reScheduleBtn.setOnClickListener {
                reScheduleClicked.invoke(allCalls)
            }

        }
        fun showButtons() {
            cancelBtn.visibility = View.VISIBLE
            reScheduleBtn.visibility = View.VISIBLE
        }

        fun hideButtons() {
            cancelBtn.visibility = View.GONE
            reScheduleBtn.visibility = View.GONE
        }


        }

//            if (isItemSwiped(adapterPosition)) {
//                // The item is swiped, so show the buttons
//                showButtons()
//            } else {
//                // The item is not swiped, so hide the buttons
//                hideButtons()
//            }




//        fun showButtons() {
//            cancelBtn.visibility = View.VISIBLE
//            reScheduleBtn.visibility = View.VISIBLE
//        }
//
//        fun hideButtons() {
//            cancelBtn.visibility = View.GONE
//            reScheduleBtn.visibility = View.GONE
//        }


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