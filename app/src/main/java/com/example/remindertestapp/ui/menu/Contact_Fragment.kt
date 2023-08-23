package com.example.remindertestapp.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.remindertestapp.databinding.ContactsBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment

class Contact_Fragment: BaseFragment() , OnClickListener {
    private var binding : ContactsBinding?=null

    private val viewModel: MenuViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ContactsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiate()

    }

    private fun initiate() {
        binding?.imgMenu?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
         //   binding?.imgMenu?.id ->
        }
    }

}