package com.example.remindertestapp.ui.ReSchedule

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.remindertestapp.databinding.RescheduleFragmentBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment

class ReScheduleFragment : BaseFragment() {
    private var binding :RescheduleFragmentBinding ?=null

    private val navArgs by navArgs <ReScheduleFragmentArgs>()

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

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

        initiate()
        observeViewModel()
        initiatSharedPreference()
    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    }

    private fun observeViewModel() {
        TODO("Not yet implemented")
    }

    private fun initiate() {
        binding?.etTopic?.setText(navArgs.myScheduleData.callTopic)
        binding?.tvDate?.text = navArgs.myScheduleData.callTime
        binding?.spinnertext?.text =navArgs.myScheduleData.expectedCallTime




    }
}