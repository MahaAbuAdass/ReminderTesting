package com.example.remindertestapp.ui.menu.invite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R
import com.example.remindertestapp.ui.menu.contacts.PhoneNumbersResponse

class NotExistingUserAdapter(
    val phoneNumbers: List<PhoneNumbersResponse?>?
) : RecyclerView.Adapter<NotExistingUserAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstName: TextView = itemView.findViewById(R.id.tv_first_name)
        private val lastName: TextView = itemView.findViewById(R.id.tv_second_name)


        fun setData(phoneNumbersResponse: PhoneNumbersResponse?) {
            firstName.text = phoneNumbersResponse?.firstName
            lastName.text = phoneNumbersResponse?.lastName

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotExistingUserAdapter.ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_cell, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotExistingUserAdapter.ItemViewHolder, position: Int) {
        holder.setData(phoneNumbers?.get(position))
    }

    override fun getItemCount() = phoneNumbers?.size ?: 0

}