//package com.example.remindertestapp.ui.homeContact.contacts
//
//import android.icu.text.SimpleDateFormat
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.remindertestapp.databinding.ScheduleFragmentBinding
//import com.example.remindertestapp.ui.base_ui.BaseFragment
//import java.util.Locale
//
//class Schedule : BaseFragment() {
//    private var binding: ScheduleFragmentBinding? = null
//
//
//
//  val date =  binding?.tvDate
//
// //   val spinner = binding?.spinner
// //   var selectedTime = binding?.tvTime
////    private var formattedDateTime: String? = null
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = ScheduleFragmentBinding.inflate(inflater, container, false)
//        return binding?.root
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//     initiate()
//    //    initiateDateAndTime()
//      //  observeViewModel()
//      //  initiatSharedPreference()
//    //    timeSpinner()
//
//    }
//
//  //  private fun initiateDateAndTime() {
//     //   val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//
//        // Combine the selectedDate and selectedTime into a single datetime string
//    //    val dateTimeString = "$date $selectedTime"
//
//// Parse the datetime string into a Date object
//    //    val date = dateFormat.parse(dateTimeString)
//
//// Format the Date object back into the desired format
//    //  val formattedDateTime = dateFormat.format(date)
//  //  }
//}