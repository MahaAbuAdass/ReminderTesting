package com.example.remindertestapp.ui.Schedule.new2.myTime

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
import com.example.remindertestapp.ui.ReSchedule.ReScheduleViewModel
import com.example.remindertestapp.ui.Schedule.ScheduleViewPagerDirections
import com.example.remindertestapp.ui.status.AcceptScheduleRequest
import com.example.remindertestapp.ui.status.StatusViewModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.second.generic.GeneralBottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MyTimeFragment : BaseFragment() {
    private var binding: MyTimeFragmentBinding? = null
    private val myTimeViewModel by viewModels<MyTimeViewModel>()
    private val statusViewModel by viewModels<StatusViewModel>()
    private val reScheduleViewModel by viewModels<ReScheduleViewModel>()

    private var adapter: MyTimeAdapter? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private var bottomSheetDialog: GeneralBottomSheetDialog<BottomSheetBinding>? = null


    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

     var meMyScheduleData : MeMyScheduleData ?=null
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

            callgetUserPendingCallsAPI()
            adapter?.notifyDataSetChanged()
//            meMyScheduleData?.let {
//                // Remove the item from the list
//                adapter?.removeItem(it)
//            }


        }
        statusViewModel.acceptScheduleResponseError.observe(viewLifecycleOwner){
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

        }

        statusViewModel.cancelScheduleResponse.observe(viewLifecycleOwner){
            callgetUserPendingCallsAPI()
            adapter?.notifyDataSetChanged()


//            meMyScheduleData?.let {
//                // Remove the item from the list
//                adapter?.removeItem(it)
//            }


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
                    coroutineScope.cancel()

                    dismiss()
//                    meMyScheduleData?.let {
//                            it1 -> adapter?.removeItem(it1) }
//                    adapter?.notifyDataSetChanged()



                }
                binding?.btnReject?.setOnClickListener {
                    callCancelAPI(meMyScheduleData)
                    coroutineScope.cancel()
                    dismiss()

                }

                binding?.btnReSchedule?.setOnClickListener {
                    callRescheduleAPI(meMyScheduleData)
                    dismiss()
                }
                binding?.imgClose?.setOnClickListener {
                    dismiss()
                }
            }
        }
            .dismissible(true).show()



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
        findNavController().navigate(ScheduleViewPagerDirections.actionNavigationNotificationsToReScheduleFragment(meMyScheduleData))
   }

    override fun onResume() { // used to prevent hit api every open the screen; only first time access it "if delete or edit
        //keep the user in same scrolling
        super.onResume()
        callgetUserPendingCallsAPI()
        }

}
