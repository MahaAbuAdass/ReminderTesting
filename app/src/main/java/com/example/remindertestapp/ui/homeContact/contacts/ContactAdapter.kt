package com.example.remindertestapp.ui.homeContact.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R

class ContactAdapter(
    val phoneNumbers: List<PhoneNumbersResponse?>?,
    val scheduleClicked: (phoneNumbersResponse: PhoneNumbersResponse) -> Unit,
    ) : RecyclerView.Adapter<ContactAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstName: TextView = itemView.findViewById(R.id.tv_first_name)
        private val number: TextView = itemView.findViewById(R.id.mobile_number)
        private val schedule : ImageView = itemView.findViewById(R.id.img_schedule)


        fun setData(phoneNumbersResponse: PhoneNumbersResponse) {
            firstName.text = phoneNumbersResponse?.firstName

            val phoneNumber = phoneNumbersResponse?.telephone
            number.text = phoneNumber


            schedule.setOnClickListener {

                phoneNumber?.let {
                    scheduleClicked.invoke(phoneNumbersResponse)
                }

            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        phoneNumbers?.get(position)?.let { holder.setData(it) }
    }

    override fun getItemCount() = phoneNumbers?.size ?: 0

}