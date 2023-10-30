package com.example.remindertestapp.ui.dashboardDailyCalls

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remindertestapp.R
import com.example.remindertestapp.databinding.DailyCallCellBinding
import com.example.remindertestapp.databinding.FragmentDailyCallsBinding
import com.example.remindertestapp.ui.ReSchedule.ReScheduleRequestModel
import com.example.remindertestapp.ui.ReSchedule.ReScheduleViewModel
import com.example.remindertestapp.ui.Schedule.new2.meTime.InformationReceiverResponseModel
import com.example.remindertestapp.ui.Schedule.new2.meTime.MeTimeAdapter
import com.example.remindertestapp.ui.Schedule.new2.myTime.MeMyScheduleData
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.status.StatusViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : BaseFragment() {

    private var binding: FragmentDailyCallsBinding? = null

    private val swipedPositions = HashSet<Int>()

    private val getDailyCallViewModel by viewModels<DashboardViewModel>()
    private val reScheduleViewModel by viewModels<ReScheduleViewModel>()
    private val statusViewModel by viewModels<StatusViewModel>()

    private var adapter: DailyCallsAdapter? = null


    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyCallsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  initiate()
        initSharedPreferences()
        observeViewModel()
        callDailyCallsApi()



    }




    private fun observeViewModel() {

        getDailyCallViewModel?.getCallResponse?.observe(viewLifecycleOwner) {
            it?.let {

                binding?.noCallsTxt?.visibility = View.INVISIBLE
                binding?.noCallsTxt?.isVisible = false
                dailyCallsAdapter(it)
            }
        }

        getDailyCallViewModel?.getCallResponseError?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }



        statusViewModel.cancelScheduleResponse.observe(viewLifecycleOwner) {
            callDailyCallsApi()
            adapter?.notifyDataSetChanged()

        }
        statusViewModel.cancelScheduleResponseError.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

        }


    }

    fun callDailyCallsApi() {
        CoroutineScope(Dispatchers.IO).launch {
            getDailyCallViewModel.getDailyCalls(sharedPreferences?.getString(KEY_NAME, "") ?: "")
        }

    }

    private fun dailyCallsAdapter(allCalls: List<DailyCalls>?) {
        val adapter = DailyCallsAdapter(allCalls, optionClicked = { dailyCalls ->

//            val customPopup = OptionsPopup(requireContext(),dailyCalls)
//            customPopup.show()
        },
            cancelClicked = { dailyCalls -> callCancelAPI(dailyCalls) },
            reScheduleClicked = { dailyCalls -> callRescheduleAPI(dailyCalls) }
        )
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemViewHolder = viewHolder as DailyCallsAdapter.ItemViewHolder
                if (direction == ItemTouchHelper.LEFT) {
                    // Swipe left, show both buttons
                    itemViewHolder.showButtons()
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding?.recyclerView)




    }

    private fun initSharedPreferences() {
        sharedPreferences = activity?.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        );
    }

//    private fun initiate() {
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    fun callRescheduleAPI(dailyCalls: DailyCalls) {
         findNavController().navigate(DashboardFragmentDirections.actionNavigationDashboardToReScheduleFragment(getMeMyScheduleDataModel(dailyCalls)) )
    }

    fun callCancelAPI(dailyCalls: DailyCalls) {
        CoroutineScope(Dispatchers.IO).launch {
            statusViewModel.cancelSchedule(
                sharedPreferences?.getString(KEY_NAME, "") ?: "",
                dailyCalls.callID.toString()
            )
        }
    }

    private fun getMeMyScheduleDataModel(dailyCalls: DailyCalls) =
        MeMyScheduleData(
            callTopic = dailyCalls.callTopic ,
            phoneNumber = dailyCalls?.userPhoneNumber,
            callTime = dailyCalls.callTime,
            expectedCallTime = dailyCalls.expectedCallTime ,
            reminderID = dailyCalls.callID
        )


}



//    inner class OptionsPopup(context: Context, DailyCalls: DailyCalls) : Dialog(context) {
//
//        private val popupView: View =
//            LayoutInflater.from(context).inflate(R.layout.daily_calls_popup, null)
//        private val reschedule: TextView = popupView.findViewById(R.id.tv_reschedule_d)
//        private val cancel: TextView = popupView.findViewById(R.id.tv_cancel_d)
//
//        init {
//            setContentView(popupView)
//            reschedule.setOnClickListener {
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    reScheduleViewModel.reSchedule(
//                        sharedPreferences?.getString(KEY_NAME, "") ?: "", ReScheduleRequestModel(
//                            callId = DailyCalls.callID,
//                            newCallTime = ""
//                        )
//                    )
//                }
//
//                reScheduleViewModel.reScheduleResponse.observe(viewLifecycleOwner) {
//                    dismiss()
//                }
//
//                reScheduleViewModel.reScheduleResponseError.observe(viewLifecycleOwner) {
//                    Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
//
//                }
//            }
//            cancel.setOnClickListener {
//                dismiss()
//            }
//        }
//    }


