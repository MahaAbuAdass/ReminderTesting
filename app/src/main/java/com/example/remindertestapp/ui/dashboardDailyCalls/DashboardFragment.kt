package com.example.remindertestapp.ui.dashboardDailyCalls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.remindertestapp.databinding.FragmentDailyCallsBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment

class DashboardFragment : BaseFragment() {

    private var binding: FragmentDailyCallsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyCallsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}