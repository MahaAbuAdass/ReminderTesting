package com.example.remindertestapp.ui.Schedule.new2.MeTime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.remindertestapp.databinding.MeTimeFragmentBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment

class MeTimeFragment : BaseFragment(){

    private var binding : MeTimeFragmentBinding?=null

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
    }
}