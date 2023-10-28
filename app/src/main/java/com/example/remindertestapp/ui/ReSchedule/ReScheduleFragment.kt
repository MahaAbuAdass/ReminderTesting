package com.example.remindertestapp.ui.ReSchedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.remindertestapp.databinding.RescheduleFragmentBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
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

lateinit var timePicker : String

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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding?.imgBack?.setOnClickListener(this)
        binding?.tvDate?.setOnClickListener(this)
        binding?.tvTimePicker?.setOnClickListener(this)


        val selectedDate = navArgs.myScheduleData.callTime

        val dateParts = selectedDate?.split("T")
        val date = dateParts?.get(0)
        val time = dateParts?.get(1)
        binding?.tvDate?.text = date
        binding?.tvTimePicker?.text=time
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.btnSend?.id -> callReScheduleAPI()
            binding?.tvDate?.id -> onSelectDateClick()
            binding?.imgBack?.id -> mainActivity.onBackPressed()
            binding?.tvTimePicker?.id -> showTimePicker()
        }
    }


        fun showTimePicker() {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                    val selectedTime = "$selectedHour:$selectedMinute"
                 //   binding?.tvTtttttttttttttime?.text = selectedTime
                    binding?.tvTimePicker?.text =  "$selectedHour-$selectedMinute"
                    timePicker = "$selectedHour-$selectedMinute"

                },
                hour,
                minute,
                true
            )

            timePickerDialog.show()
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

         //       Toast.makeText(requireContext(), selectedDate, Toast.LENGTH_LONG).show()
                binding?.tvDate?.text = "$selectedYear-${selectedMonth + 1}-$selectedDay"
        //        date?.text = selectedDate

            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


    fun getFormatDateTime(): String {
        val time = timePicker.split("-")
        val date = selectedDate.split("-")
        val calendar = Calendar.getInstance()

        calendar.set(date[0].toInt(), date[1].toInt(), date[2].toInt(), time[0].toInt(), time[1].toInt())
        return dateTimeFormatter.format(calendar.time)
    }

    val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)

}
