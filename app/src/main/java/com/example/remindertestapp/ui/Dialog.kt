//package com.example.remindertestapp.ui
//
//import android.view.View
//import androidx.appcompat.app.AlertDialog
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context
//
//private fun showDialog(message: String, title: String) {
//    val dialogBuilder = AlertDialog.Builder(context)
//    dialogBuilder.setTitle(title)
//    dialogBuilder.setMessage(message)
//    dialogBuilder.setPositiveButton("OK") { dialog, which ->
//        // Do something when the "OK" button is clicked
//        dialog.dismiss()
//    }
//    val dialog = dialogBuilder.create()
//    dialog.show()
//}