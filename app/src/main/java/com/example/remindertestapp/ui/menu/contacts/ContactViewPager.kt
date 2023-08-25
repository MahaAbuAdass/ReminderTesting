package com.example.remindertestapp.ui.menu.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.databinding.ContactViewPagerBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.menu.invite.InviteFragment
import com.google.android.material.tabs.TabLayoutMediator

class ContactViewPager : BaseFragment() , OnClickListener{
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

    }