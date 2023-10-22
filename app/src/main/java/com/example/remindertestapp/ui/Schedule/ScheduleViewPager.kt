package com.example.remindertestapp.ui.Schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.remindertestapp.databinding.ScheduleViewPagerBinding

import com.example.remindertestapp.ui.Schedule.new2.meTime.MeTimeFragment
import com.example.remindertestapp.ui.Schedule.new2.myTime.MyTimeFragment
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.ContactViewPagesAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ScheduleViewPager : BaseFragment() {

    private var binding: ScheduleViewPagerBinding? = null

    private val tabTitles by lazy {
        arrayOf("ME Time", "My Time")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ScheduleViewPagerBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = activity?.let {
            ContactViewPagesAdapter(it, MeTimeFragment(), MyTimeFragment())
        }

        binding?.viewPager?.adapter = adapter

        TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}