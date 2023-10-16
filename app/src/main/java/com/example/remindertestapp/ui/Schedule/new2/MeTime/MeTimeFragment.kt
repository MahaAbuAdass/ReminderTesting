package com.example.remindertestapp.ui.Schedule.new2.MeTime

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.databinding.BottomSheetBinding
import com.example.remindertestapp.databinding.MeTimeFragmentBinding
import com.example.remindertestapp.ui.Schedule.new2.MyTime.MeMyScheduleData
import com.example.remindertestapp.ui.Schedule.new2.MyTime.MyTimeAdapter
import com.example.remindertestapp.ui.Schedule.new2.MyTime.MyTimeViewModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.second.generic.GeneralBottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MeTimeFragment : BaseFragment(){

    private var binding : MeTimeFragmentBinding?=null

    private val meTimeViewModel by viewModels<MeTimeViewModel>()


    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MeTimeFragmentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiatSharedPreference()
        callgetCallsAPI()
        observeViewModel()
    }

    fun  callgetCallsAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            meTimeViewModel.getReceivedCallPending(sharedPreferences?.getString(KEY_NAME, "") ?: "")
        }
    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun observeViewModel() {
        meTimeViewModel.getCallsResponse.observe(viewLifecycleOwner) {
            it?.let { //
                meTimeAdapter(it)
            }

        }
        meTimeViewModel.getCallsResponseError?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun meTimeAdapter(Data: List<InformationReceiverResponseModel?>?) {
        val adapter = MeTimeAdapter(Data, itemClicked = {
            bottomSheet(it)
        })
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
    }


    fun bottomSheet(informationReceiverResponseModel: InformationReceiverResponseModel) {
        object : GeneralBottomSheetDialog<BottomSheetBinding>(mainActivity) {
            override fun getViewBinding() = BottomSheetBinding.inflate(layoutInflater)

            override fun onLayoutCreated(view: GeneralBottomSheetDialog<BottomSheetBinding>) {
                binding?.tvUserName?.text = informationReceiverResponseModel.userName
                binding?.tvTopicText?.text=informationReceiverResponseModel.callTopic
                binding?.tvDateAndTime?.text=informationReceiverResponseModel.callTime
                binding?.tvDuration?.text = informationReceiverResponseModel.callTime


//                binding.btnAccept.setOnClickListener {
//                    callAcceptAPI(meMyScheduleData)
//                    dismiss()
//
//                }
//                binding?.btnReject?.setOnClickListener {
//                    callCancelAPI(meMyScheduleData)
//                    dismiss()
//
//                }
//
//                binding?.btnReSchedule?.setOnClickListener { }
//                callRescheduleAPI(meMyScheduleData)
//                dismiss()

            }


        }.dismissible().show()



    }



}

