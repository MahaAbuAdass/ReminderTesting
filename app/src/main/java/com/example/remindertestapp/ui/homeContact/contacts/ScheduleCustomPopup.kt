package com.example.remindertestapp.ui.homeContact.contacts

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EdgeEffect
import android.widget.EditText
import com.example.remindertestapp.R

class ScheduleCustomPopup (context: Context) : Dialog(context) {

    private val popupView: View = LayoutInflater.from(context).inflate(R.layout.schedule_popup, null)


    private val topic: EditText = popupView.findViewById(R.id.et_topic)
    private val time: EditText = popupView.findViewById(R.id.et_time)
    private val send : Button = popupView.findViewById(R.id.btn_send)

    init {
        setContentView(popupView)
        send.setOnClickListener {


            // Dismiss the popup
            dismiss()
        }


    }
}




