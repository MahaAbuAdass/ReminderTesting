package com.example.remindertestapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.databinding.ContactViewPagerBinding
import com.example.remindertestapp.databinding.FragmentHomeBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.menu.contacts.ContactFragment
import com.example.remindertestapp.ui.menu.contacts.ContactViewPagerDirections
import com.example.remindertestapp.ui.menu.contacts.ContactViewPagesAdapter
import com.example.remindertestapp.ui.menu.invite.InviteFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment() , View.OnClickListener {
    private lateinit var binding: ContactViewPagerBinding

    private val tabTitles by lazy {
        arrayOf("Contacts", "Invite")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContactViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiate()

        val adapter = activity?.let {
            ContactViewPagesAdapter(it, ContactFragment(), InviteFragment())
        }

        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

    }
    private fun initiate() {
        binding?.imgMenu?.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.imgMenu?.id -> findNavController().navigate(ContactViewPagerDirections.actionViewPagerContactToMenu())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //binding = null
    }
}