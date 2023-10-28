package com.example.remindertestapp.ui.dashboardDailyCalls

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R


class DailyCallsAdapter (
    val allCalls: List<DailyCalls>? ,
    val optionClicked: (allCalls: DailyCalls) -> Unit,
    val cancelClicked :(allCalls: DailyCalls ) -> Unit,
    val reScheduleClicked :(allCalls: DailyCalls ) -> Unit

) : RecyclerView.Adapter<DailyCallsAdapter.ItemViewHolder>() {


    private val swipedPositions = HashSet<Int>()
    fun isItemSwiped(position: Int): Boolean {
        return swipedPositions.contains(position)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tv_name_d)
        private val time: TextView = itemView.findViewById(R.id.tv_time_d)

        private val cancelBtn : Button = itemView.findViewById(R.id.btn_reject_dc)
        private val reScheduleBtn: Button = itemView.findViewById(R.id.btn_reSchedule_dc)


        fun setData(allCalls: DailyCalls) {
            userName.text = allCalls.userName
            time.text = allCalls.expectedCallTime



            cancelBtn.setOnClickListener{
                cancelClicked.invoke(allCalls)
            }

            reScheduleBtn.setOnClickListener {
                reScheduleClicked.invoke(allCalls)
            }


            if (isItemSwiped(adapterPosition)) {
                // The item is swiped, so show the buttons
                showButtons()
            } else {
                // The item is not swiped, so hide the buttons
                hideButtons()
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_call_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dailyCalls = allCalls?.get(position)
        dailyCalls?.let {
            holder.setData(it)
            if (isItemSwiped(position)) {
                // The item is swiped, so show the buttons
                holder.showButtons()
            } else {
                // The item is not swiped, so hide the buttons
                holder.hideButtons()
            }
        }


        allCalls?.get(position)?.let { holder.setData(it) }

    }

    override fun getItemCount() = allCalls?.size ?: 0

}