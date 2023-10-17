package com.example.remindertestapp.ui.Schedule.new2.MyTime

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.databinding.BottomSheetBinding
import com.example.remindertestapp.databinding.MyTimeFragmentBinding
import com.example.remindertestapp.ui.ReSchedule.ReScheduleRequestModel
import com.example.remindertestapp.ui.ReSchedule.ReScheduleViewModel
import com.example.remindertestapp.ui.Status.Accept.AcceptScheduleRequest
import com.example.remindertestapp.ui.Status.Accept.StatusViewModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.contacts.PhoneNumbersResponse
import com.example.second.generic.GeneralBottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyTimeFragment : BaseFragment() {
    private var binding: MyTimeFragmentBinding? = null
    private val myTimeViewModel by viewModels<MyTimeViewModel>()
    private val statusViewModel by viewModels<StatusViewModel>()
    private val reScheduleViewModel by viewModels<ReScheduleViewModel>()

    private var adapter: MyTimeAdapter? = null

    private var bottomSheetDialog: GeneralBottomSheetDialog<BottomSheetBinding>? = null



    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyTimeFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiatSharedPreference()
        callgetUserPendingCallsAPI()
        observeViewModel()

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
            it?.let { //
                pendingCallsAdapter(it)
            }

        }
        myTimeViewModel.getPendingCallsResponseError?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }


        statusViewModel.acceptScheduleResponse.observe(viewLifecycleOwner){

        }
        statusViewModel.acceptScheduleResponseError.observe(viewLifecycleOwner){
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

        }

        statusViewModel.cancelScheduleResponse.observe(viewLifecycleOwner){
        //    adapter?.scheduleData?.remove(it)
            adapter?.notifyDataSetChanged()

        }
        statusViewModel.cancelScheduleResponseError.observe(viewLifecycleOwner){
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

        }



    }

    private fun pendingCallsAdapter(scheduleData: List<MeMyScheduleData?>?) {
        val adapter = MyTimeAdapter(scheduleData, itemClicked = {
            bottomSheet(it)
        })
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
    }


    fun bottomSheet(meMyScheduleData: MeMyScheduleData) {
        object : GeneralBottomSheetDialog<BottomSheetBinding>(mainActivity) {
            override fun getViewBinding() = BottomSheetBinding.inflate(layoutInflater)

            override fun onLayoutCreated(view: GeneralBottomSheetDialog<BottomSheetBinding>) {
                binding?.tvUserName?.text = meMyScheduleData.userName
                binding?.tvTopicText?.text=meMyScheduleData.callTopic
                binding?.tvDateAndTime?.text=meMyScheduleData.callTime
                binding?.tvDuration?.text = meMyScheduleData.expectedCallTime


                binding.btnAccept.setOnClickListener {

                    callAcceptAPI(meMyScheduleData)
                    dismiss()

                }
                binding?.btnReject?.setOnClickListener {
                    callCancelAPI(meMyScheduleData)
                    dismiss()

                }

                binding?.btnReSchedule?.setOnClickListener { }
                callRescheduleAPI(meMyScheduleData)
                dismiss()

            }


        }.dismissible().show()



    }

    fun callAcceptAPI(meMyScheduleData: MeMyScheduleData) {
        CoroutineScope(Dispatchers.IO).launch {
            statusViewModel.acceptSchedule(
                sharedPreferences?.getString(KEY_NAME, "") ?: "",
                AcceptScheduleRequest(
                    reminderId = meMyScheduleData.reminderID
                )
            )
        }

    }

    fun  callCancelAPI(meMyScheduleData : MeMyScheduleData){
        CoroutineScope(Dispatchers.IO).launch {
            statusViewModel.cancelSchedule(sharedPreferences?.getString(KEY_NAME, "") ?: "",
                meMyScheduleData.reminderID.toString()
            )
        }

    }

    fun callRescheduleAPI(meMyScheduleData : MeMyScheduleData) {
        findNavController().navigate(MyTimeFragmentDirections.actionMyTimeFragmentToReScheduleFragment(meMyScheduleData))


//        CoroutineScope(Dispatchers.IO).launch {
//     reScheduleViewModel.reSchedule(sharedPreferences?.getString(KEY_NAME, "") ?: "",
//         ReScheduleRequestModel(
//             callId = ,
//             newCallTime = null
//         )
//     )
//}
    }

}
