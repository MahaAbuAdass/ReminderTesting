package com.example.remindertestapp.ui.Schedule.new2.MyTime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.remindertestapp.databinding.MyTimeFragmentBinding
import com.example.remindertestapp.ui.Schedule.MyTime.MyNameFragment
import com.example.remindertestapp.ui.Schedule.MyTime.MyStatusFragment
import com.example.remindertestapp.ui.Schedule.MyTime.MyTimeTimeFragment
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.ContactViewPagesAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyTimeFragment : BaseFragment() {
    private var binding : MyTimeFragmentBinding?=null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyTimeFragmentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}