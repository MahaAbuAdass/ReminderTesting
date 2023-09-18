package com.example.remindertestapp.ui.homeContact.contacts

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.ParseException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.remindertestapp.databinding.CreateAccountFragmentBinding
import com.example.remindertestapp.databinding.ScheduleFragmentBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Locale

class ScheduleFragment : BaseFragment(), OnClickListener {
    private var binding: ScheduleFragmentBinding? = null


    // private val scheduleViewModel by viewModels<ContactViewModel>()
    //private val navargs by navArgs<ScheduleFragmentArgs>()

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    //   private var formattedDateTime: String? = null


    val date = binding?.tvDate
    val spinner = binding?.spinner
    var tvSelectedTime = binding?.timePicker
    val tvExpectedTime = binding?.tvExpectedTime

    var selectedTime = ""
    var selectedDate =""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScheduleFragmentBinding.inflate(inflater, container, false)


        return binding?.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timeSpinner()

        initiate()
        initiateDateAndTime()
        observeViewModel()
        initiatSharedPreference()
    }

    private fun initiateDateAndTime() {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        // Combine the selectedDate and selectedTime into a single datetime string
        val dateTimeString = "$selectedDate $selectedTime"


        try {
            // Parse the datetime string into a Date object
            val parseDate = dateFormat.parse(dateTimeString)

            // Format the Date object back into the desired format
            val formattedDateTime = dateFormat.format(parseDate)
        } catch (e: ParseException) {
            // Handle the exception, e.g., invalid date or time format
            e.printStackTrace()
        }
    }












    private fun timeSpinner() {
        val options = listOf("2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22", "24")


        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, options)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = options[position]

               binding?.tvExpectedTime?.text = selectedItem
                tvExpectedTime?.text=selectedItem

                spinner?.visibility = View.GONE // Hide the Spinner after selection
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    fun showDropdown() {
        spinner?.visibility = View.VISIBLE


    }

    private fun callScheduleAPI() {

//        CoroutineScope(Dispatchers.IO).launch {
//
//            scheduleViewModel.makeSchedule(
//                ScheduleRequestModel(
//                    callTime =  formattedDateTime    ,
//                    callTopic = binding?.etTopic?.text.toString(),
//                    expectedCallTime = binding?.tvTime?.text.toString(),
//                    recievedUserphoneNumber = ""
//                    //navargs.phonenumbers.telephone
//
//                ),
//                sharedPreferences?.getString(KEY_NAME, "") ?: ""
//
//
//            )
//        }
    }


    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun observeViewModel() {
//        scheduleViewModel.scheduleResponse.observe(viewLifecycleOwner) {
//
//        }
//        scheduleViewModel.scheduleResponseError.observe(viewLifecycleOwner) {
//            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
//        }
    }

    private fun initiate() {
        binding?.btnSend?.setOnClickListener(this)
        binding?.tvExpectedTime?.setOnClickListener(this)
        binding?.timePicker?.setOnClickListener(this)
        binding?.tvDate?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.btnSend?.id -> callScheduleAPI()
            binding?.tvExpectedTime?.id -> showDropdown()
            binding?.tvDate?.id -> onSelectDateClick()
            binding?.timePicker?.id -> getTime()
        }
    }

    private fun getTime() {
        binding?.timePicker?.setOnTimeChangedListener { _, hourOfDay, minute ->
            // Handle the selected time here
            selectedTime = formatTime(hourOfDay, minute)

        }
    }

    // Helper function to format the selected time
    private fun formatTime(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val sdf = SimpleDateFormat("hh:mm a")
        return sdf.format(calendar.time)
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
                selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                binding?.tvDate?.text = selectedDate
                date?.text = selectedDate
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


}