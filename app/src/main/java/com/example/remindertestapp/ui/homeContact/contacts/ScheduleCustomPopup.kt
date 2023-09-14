package com.example.remindertestapp.ui.homeContact.contacts

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EdgeEffect
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.remindertestapp.R
import com.example.remindertestapp.ui.account.createaccount.CreateAccountViewModel
import com.example.remindertestapp.ui.account.login.LoginViewModel
import com.example.remindertestapp.ui.base_ui.BaseFragment

class ScheduleCustomPopup (context: Context, override val viewModelStore: ViewModelStore) : Dialog(context), ViewModelStoreOwner {

private var contactViewModel : ContactViewModel ?=null

    private val popupView: View =
        LayoutInflater.from(context).inflate(R.layout.schedule_popup, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the ViewModelProvider from the parent Activity or Fragment
    val activity = context as? ContactFragment


    }

    private val topic: EditText = popupView.findViewById(R.id.et_topic)
    private val time: EditText = popupView.findViewById(R.id.et_time)
    private val send: Button = popupView.findViewById(R.id.btn_send)

    init {
        setContentView(popupView)
        send.setOnClickListener {






            // Dismiss the popup
            dismiss()
        }
    }
}




