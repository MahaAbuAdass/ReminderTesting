package com.example.remindertestapp.ui.Schedule.new2.MyTime

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.databinding.MyTimeFragmentBinding
import com.example.remindertestapp.ui.Schedule.MyTime.MyNameFragment
import com.example.remindertestapp.ui.Schedule.MyTime.MyStatusFragment
import com.example.remindertestapp.ui.Schedule.MyTime.MyTimeTimeFragment
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.ContactViewPagesAdapter
import com.example.remindertestapp.ui.homeContact.contacts.PhoneNumbersResponse
import com.example.remindertestapp.ui.homeContact.invite.CustomPopup
import com.example.remindertestapp.ui.homeContact.invite.InviteViewModel
import com.example.remindertestapp.ui.homeContact.invite.NotExistingUserAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyTimeFragment : BaseFragment() {
    private var binding : MyTimeFragmentBinding?=null
    private val myTimeViewModel by viewModels<MyTimeViewModel>()


    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyTimeFragmentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initiatSharedPreference()
        callgetUserPendingCallsAPI()


    }

    private fun callgetUserPendingCallsAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            myTimeViewModel.getUserPendingCalls(sharedPreferences?.getString(KEY_NAME, "") ?: "")
        }
    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun observeViewModel() {
        myTimeViewModel.getPendingCallsResponse.observe(viewLifecycleOwner) {
            it?.let {
      //          pendingCallsAdapter(it)
            }

        }
        myTimeViewModel.getPendingCallsResponseError?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }

    }
//    private fun pendingCallsAdapter() {
//   //     val adapter = MyTimeAdapter
//
//        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
//        binding?.recyclerView?.adapter = adapter
//    }
//}
}