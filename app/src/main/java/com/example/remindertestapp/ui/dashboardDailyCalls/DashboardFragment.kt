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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.R
import com.example.remindertestapp.databinding.FragmentDailyCallsBinding
import com.example.remindertestapp.ui.ReSchedule.ReScheduleRequestModel
import com.example.remindertestapp.ui.ReSchedule.ReScheduleViewModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : BaseFragment() {

    private var binding: FragmentDailyCallsBinding? = null
    private val getDailyCallViewModel by viewModels<DashboardViewModel>()
    private val reScheduleViewModel by viewModels<ReScheduleViewModel>()


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

        initiate()
        initSharedPreferences()
        observeViewModel()
    }

    private fun observeViewModel() {

        getDailyCallViewModel?.getCallResponse?.observe(viewLifecycleOwner) {
            it?.let {
                dailyCallsAdapter(it)
            }
        }

        getDailyCallViewModel?.getCallResponseError?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun dailyCallsAdapter(allCalls: List<DailyCalls>?) {
        val adapter = DailyCallsAdapter(allCalls, optionClicked = {DailyCalls->

            val customPopup = OptionsPopup(requireContext(),DailyCalls)
            customPopup.show()
        }
        )
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
    }

    private fun initSharedPreferences() {
        sharedPreferences = activity?.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        );
    }

    private fun initiate() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    inner class OptionsPopup(context: Context, DailyCalls: DailyCalls) : Dialog(context) {

        private val popupView: View =
            LayoutInflater.from(context).inflate(R.layout.daily_calls_popup, null)
        private val reschedule: TextView = popupView.findViewById(R.id.tv_reschedule_d)
        private val cancel: TextView = popupView.findViewById(R.id.tv_cancel_d)

        init {
            setContentView(popupView)
            reschedule.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    reScheduleViewModel.reSchedule(
                        sharedPreferences?.getString(KEY_NAME, "") ?: "", ReScheduleRequestModel(
                            callId = DailyCalls.callID,
                            newCallTime = ""
                        )
                    )
                }

                reScheduleViewModel.reScheduleResponse.observe(viewLifecycleOwner) {
                    dismiss()
                }

                reScheduleViewModel.reScheduleResponseError.observe(viewLifecycleOwner) {
                    Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

                }
            }
            cancel.setOnClickListener {
                dismiss()
            }
        }
    }


}