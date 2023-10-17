package com.example.remindertestapp.ui.ReSchedule

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.remindertestapp.databinding.RescheduleFragmentBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.contacts.ContactViewModel
import com.example.remindertestapp.ui.homeContact.contacts.ScheduleRequestModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class ReScheduleFragment : BaseFragment() , OnClickListener {
    private var binding :RescheduleFragmentBinding ?=null

    private val navArgs by navArgs <ReScheduleFragmentArgs>()
    private val reScheduleViewModel by viewModels<ReScheduleViewModel>()


    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null



    val date = binding?.tvDate
    var selectedTime = ""
    var selectedDate =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= RescheduleFragmentBinding.inflate(inflater,container,false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTime()
        initiate()
        observeViewModel()
        initiatSharedPreference()
    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    }

    private fun observeViewModel() {
        reScheduleViewModel.reScheduleResponse.observe(viewLifecycleOwner) {
            mainActivity.onBackPressed()

        }
        reScheduleViewModel.reScheduleResponseError.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

        }
    }

    private fun initiate() {
        binding?.btnSend?.setOnClickListener(this)
//        binding?.spinnertext?.setOnClickListener(this)
        binding?.imgBack?.setOnClickListener(this)
        binding?.tvDate?.setOnClickListener(this)

        binding?.etTopic?.setText(navArgs.myScheduleData.callTopic)
        binding?.tvDate?.text = getDateFormat()
//        binding?.spinnertext?.text =navArgs.myScheduleData.expectedCallTime

    }

    fun getDateFormat(): String {
        val selectedDate = navArgs.myScheduleData.callTime
        val dateParts = selectedDate?.split("-")
        val calendar = Calendar.getInstance()

        dateParts?.get(2)?.let { calendar.set(
            (dateParts?.get(0)?.toInt() ?: "") as Int
        , (("dateParts?.get(1)?.toInt() ?:  - 1" )as Int)
            , it.toInt()) } // Adjust month by -1
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return dateFormat.format(calendar.time)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.btnSend?.id -> callReScheduleAPI()
            binding?.tvDate?.id -> onSelectDateClick()
            binding?.imgBack?.id -> mainActivity.onBackPressed()
        }
    }






    private fun callReScheduleAPI() {
        CoroutineScope(Dispatchers.IO).launch {

            reScheduleViewModel.reSchedule(
                sharedPreferences?.getString(KEY_NAME, "") ?: "",
                ReScheduleRequestModel(
                    callId = navArgs.myScheduleData.reminderID ,
                    newCallTime = getFormatDateTime()
                ))
        }
    }


    fun getFormatDateTime(): String {
        val time = selectedTime.split("-")
        val date = selectedDate.split("-")
        val calendar = Calendar.getInstance()

        calendar.set(date[0].toInt(), date[1].toInt(), date[2].toInt(), time[0].toInt(), time[1].toInt())
        return dateTimeFormatter.format(calendar.time)
    }

    val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)

    fun onSelectDateClick() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                // Handle the selected date
                selectedDate = "$selectedYear-${selectedMonth }-$selectedDay"

                Toast.makeText(requireContext(), selectedDate, Toast.LENGTH_LONG).show()
                binding?.tvDate?.text = selectedDate
                date?.text = selectedDate

            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun getTime() {
        val calendar = Calendar.getInstance()
        val time = binding?.timePicker
        time?.setIs24HourView(true)

        time?.hour = calendar.get(Calendar.HOUR_OF_DAY)
        time?.minute = calendar.get(Calendar.MINUTE)

        val hourOfDay = time?.hour
        val minute = time?.minute
        selectedTime= "$hourOfDay-$minute"


        time?.setOnTimeChangedListener { _, hourOfDay, minute ->
            // Handle the selected time here
            selectedTime = "$hourOfDay-$minute"
        }

    }
}