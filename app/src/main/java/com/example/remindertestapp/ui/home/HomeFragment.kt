package com.example.remindertestapp.ui.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.navigation.fragment.findNavController
//import com.example.remindertestapp.Manifest
import com.example.remindertestapp.databinding.ContactViewPagerBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.menu.contacts.ContactFragment
import com.example.remindertestapp.ui.menu.contacts.ContactViewPagesAdapter
import com.example.remindertestapp.ui.menu.invite.InviteFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment(), View.OnClickListener {
    private lateinit var binding: ContactViewPagerBinding


    private lateinit var permissionLauncher : ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted = false


    private val tabTitles by lazy {
        arrayOf("Contacts", "Invite")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = ContactViewPagerBinding.inflate(inflater, container, false)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){

            isReadPermissionGranted = it[Manifest.permission.READ_CONTACTS]?: isReadPermissionGranted
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestContactPermission()
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
            binding?.imgMenu?.id -> findNavController().navigate(HomeFragmentDirections.actionViewPagerContactToMenu())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //binding = null
    }



    private fun requestContactPermission() {
 isReadPermissionGranted = ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED

        val permissionRequest : MutableList<String> = ArrayList()
        if (! isReadPermissionGranted) {
      permissionRequest.add(Manifest.permission.READ_CONTACTS)
        }
        if (permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

    }
}