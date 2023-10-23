package com.example.remindertestapp.ui.Schedule.new2.meTime

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
import com.example.remindertestapp.databinding.BottomSheetMeBinding
import com.example.remindertestapp.databinding.MeTimeFragmentBinding
import com.example.remindertestapp.ui.Schedule.ScheduleViewPagerDirections
import com.example.remindertestapp.ui.Schedule.new2.myTime.MeMyScheduleData
import com.example.remindertestapp.ui.Status.StatusViewModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.second.generic.GeneralBottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MeTimeFragment : BaseFragment() {

    private var binding: MeTimeFragmentBinding? = null

    private val meTimeViewModel by viewModels<MeTimeViewModel>()
    private val statusViewModel by viewModels<StatusViewModel>()


    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MeTimeFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiatSharedPreference()
        observeViewModel()
    }

    fun callgetCallsAPI() {
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

        statusViewModel.cancelScheduleResponse.observe(viewLifecycleOwner){
            //    adapter?.scheduleData?.remove(it)
          //  adapter?.notifyDataSetChanged()

        }
        statusViewModel.cancelScheduleResponseError.observe(viewLifecycleOwner){
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
        object : GeneralBottomSheetDialog<BottomSheetMeBinding>(mainActivity) {
            override fun getViewBinding() = BottomSheetMeBinding.inflate(layoutInflater)

            override fun onLayoutCreated(view: GeneralBottomSheetDialog<BottomSheetMeBinding>) {
                binding?.tvUserName?.text = informationReceiverResponseModel.userName
                binding?.tvTopicText?.text = informationReceiverResponseModel.callTopic
                binding?.tvDateAndTime?.text = informationReceiverResponseModel.callTime
                //     binding?.tvDuration?.text = informationReceiverResponseModel


                binding?.btnReject?.setOnClickListener {
                    callCancelAPI(informationReceiverResponseModel)
                    dismiss()
fun removerow(informationReceiverResponseModel: InformationReceiverResponseModel) {}
                }

                binding?.btnReSchedule?.setOnClickListener { }
                callRescheduleAPI(informationReceiverResponseModel)
                dismiss()
            }
        }.dismissible().show()
    }


    fun callCancelAPI(informationReceiverResponseModel: InformationReceiverResponseModel) {
        CoroutineScope(Dispatchers.IO).launch {
            statusViewModel.cancelSchedule(
                sharedPreferences?.getString(KEY_NAME, "") ?: "",
                informationReceiverResponseModel.reminderID.toString()
            )
        }
    }

    fun callRescheduleAPI(informationReceiverResponseModel: InformationReceiverResponseModel) {
   //findNavController().navigate(ScheduleViewPagerDirections.actionScheduleViewPagerToReScheduleFragment(informationReceiverResponseModel))
    }

    override fun onResume() { // used to prevent hit api every open the screen; only first time access it "if delete or edit
        //keep the user in same scrolling
        super.onResume()
        callgetCallsAPI()

    }

}