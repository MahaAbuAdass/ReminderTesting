package com.example.remindertestapp.ui.homeContact.invite

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.example.remindertestapp.R

import android.app.Dialog
import com.example.remindertestapp.ui.homeContact.contacts.PhoneNumbersResponse


class CustomPopup(context: Context, phoneNumbers: PhoneNumbersResponse?) : Dialog(context) {

    private val popupView: View = LayoutInflater.from(context).inflate(R.layout.invite_popup, null)
    private val btnShareSMS: Button = popupView.findViewById(R.id.btn_share_sms)
    private val btnShareWhatsApp: Button = popupView.findViewById(R.id.btn_share_whatsApp)

    init {
        setContentView(popupView)

        btnShareSMS.setOnClickListener {
            // Handle the SMS sharing logic here
            // For example, you can open an SMS intent to compose a message
            // Intent to send SMS
            val smsIntent = Intent(Intent.ACTION_VIEW)
            smsIntent.data = Uri.parse("sms:")
            smsIntent.putExtra("sms_body", "Hello, check out this cool app!")
            context.startActivity(smsIntent)

            // Dismiss the popup
            dismiss()
        }

        btnShareWhatsApp.setOnClickListener {
            // Handle the WhatsApp sharing logic here
            // For example, you can open a WhatsApp intent to share a message
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hello, check out this cool app!")
            try {
                context.startActivity(whatsappIntent)
            } catch (e: ActivityNotFoundException) {
                // Handle case where WhatsApp is not installed
            }

            // Dismiss the popup
            dismiss()
        }
    }
}




