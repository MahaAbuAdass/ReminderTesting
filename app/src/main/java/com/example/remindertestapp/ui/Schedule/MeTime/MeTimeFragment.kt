package com.example.remindertestapp.ui.Schedule.MeTime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.remindertestapp.databinding.MeTimeFragmentBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.ContactViewPagesAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MeTimeFragment : BaseFragment() {
    private var binding : MeTimeFragmentBinding?=null


    private val tabTitles by lazy {
        arrayOf("Name", "Time")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MeTimeFragmentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = activity?.let {
            ContactViewPagesAdapter(it, MeNameFragment(), MeTimeTimeFragment())
        }

        binding?.viewPager?.adapter = adapter

        TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

    }

}