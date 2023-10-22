package com.example.remindertestapp.ui.generic

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.remindertestapp.R
import com.example.remindertestapp.databinding.DialogBinding

class CustomDialog(private val context: Context) {
    fun showCustomDialog(title: String, message: String) {


        val dialogBinding = DialogBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root


        dialogBinding?.dialogTitle?.text = title
        dialogBinding?.dialogMessage?.text = message

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogBinding?.dialogOkButton?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
