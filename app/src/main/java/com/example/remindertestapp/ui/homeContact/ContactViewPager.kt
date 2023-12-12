package com.example.remindertestapp.ui.homeContact

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.core.content.ContextCompat

import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.R
//import com.example.remindertestapp.Manifest
import com.example.remindertestapp.databinding.ContactViewPagerBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.contacts.ContactFragment
import com.example.remindertestapp.ui.homeContact.invite.InviteFragment
import com.google.android.material.tabs.TabLayoutMediator

class ContactViewPager : BaseFragment(), View.OnClickListener {
    private lateinit var binding: ContactViewPagerBinding

    private val tabTitles by lazy {
        val contact = resources.getString(R.string.contact)
        val invite = resources.getString(R.string.invite)

        arrayOf(contact, invite)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = ContactViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //   requestContactPermission()
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
        binding?.imgNotification?.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.imgMenu?.id -> findNavController().navigate(ContactViewPagerDirections.actionViewPagerContactToMenu())
            binding?.imgNotification?.id -> findNavController().navigate(ContactViewPagerDirections.actionNavigationHomeToNotifications())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //binding = null
    }
}