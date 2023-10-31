package com.example.remindertestapp.ui.Norification

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.databinding.DialogNotificationBinding
import com.example.remindertestapp.databinding.NotificaitonFragmentBinding
import com.example.remindertestapp.ui.Schedule.new2.myTime.MyTimeAdapter
import com.example.remindertestapp.ui.base_ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationFragment : BaseFragment(), OnClickListener {
    private var binding: NotificaitonFragmentBinding? = null
    private val notificationViewModel by viewModels<NotificationViewModel>()

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    private var adapter: NotificationAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NotificaitonFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiate()
        initSharedPreferences()
        observeViewModel()
        callGetNotificationApi()

    }


    private fun callGetNotificationApi() {
        CoroutineScope(Dispatchers.IO).launch {
            notificationViewModel.getNotification(sharedPreferences?.getString(KEY_NAME, "") ?: "")
        }
    }

    private fun observeViewModel() {

        notificationViewModel.notificationResponse.observe(viewLifecycleOwner) {
            notificationAdapter(it)
        }

        notificationViewModel.notificationResponseError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }



            notificationViewModel.clearAllNotificationResponse.observe(viewLifecycleOwner) { response ->

//                if (response == null) {
//                    Toast.makeText(requireContext(), "No Notifications found", Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//                    val notificationManager =
//                        requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                    notificationManager.cancelAll()
//                }
                callGetNotificationApi()
                adapter?.notifyDataSetChanged()
            }


        notificationViewModel.clearAllNotificationResponseError.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }




        notificationViewModel.removeSingleNotificationResponse.observe(viewLifecycleOwner) {
            callGetNotificationApi()
            adapter?.notifyDataSetChanged()

        }

        notificationViewModel.clearSingleNotificationResponseError.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }

    }


    private fun notificationAdapter(notifications: List<NotificationModel>?) {
        val adapter = NotificationAdapter(notifications, deleteNotificationRow = {notificationModelResponse ->

            CoroutineScope(Dispatchers.IO).launch {
                notificationViewModel.removeSignleNotification(
                    notificationModelResponse.notificationId,
                    sharedPreferences?.getString(KEY_NAME, "") ?: ""
                )

            }  })


        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter

    }

    private fun initSharedPreferences() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.tvClearAll?.id -> showCustomDialog("Notification" , "Are you sure you want to clear all Notifications")
            binding?.btnBack?.id -> mainActivity.onBackPressed()
        }


    }


    private fun initiate() {
        binding?.tvClearAll?.setOnClickListener(this)
        binding?.btnBack?.setOnClickListener(this)
    }

    fun callClearNotificationApi() {
            CoroutineScope(Dispatchers.IO).launch{
     notificationViewModel.removeAllNotification(sharedPreferences?.getString(KEY_NAME, "") ?: "",2)
            }
    }



    fun showCustomDialog(title: String, message: String) {

        val dialogBinding = DialogNotificationBinding.inflate(LayoutInflater.from(context))
        val dialogView = dialogBinding.root

        dialogBinding?.dialogTitle?.text = title
        dialogBinding?.dialogMessage?.text = message

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogBinding?.dialogAcceptButton?.setOnClickListener {
            callClearNotificationApi()
            dialog.dismiss()
        }
        dialogBinding?.dialogCancelButton?.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }


}