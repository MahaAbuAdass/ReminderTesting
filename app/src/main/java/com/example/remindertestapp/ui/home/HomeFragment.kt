package com.example.remindertestapp.ui.home

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.databinding.ContactViewPagerBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.menu.contacts.ContactFragment
import com.example.remindertestapp.ui.menu.contacts.ContactViewPagesAdapter
import com.example.remindertestapp.ui.menu.invite.InviteFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment(), View.OnClickListener {
    private lateinit var binding: ContactViewPagerBinding



    private val tabTitles by lazy {
        arrayOf("Contacts", "Invite")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = ContactViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   checkContactPermission()
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


    /* fun checkContactPermission(){
        // Check if permission is granted

        val permission = Manifest.permission.READ_CONTACTS
        val requestCode = 123 // You can use any code here

        if (ContextCompat.checkSelfPermission(requireActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
        } else {
            // Permission is already granted, proceed with accessing contacts
            // Access and upload contacts here
        }
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 123) { // Make sure to match the request code
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with accessing contacts
                // Access and upload contacts here
            } else {
                // Permission denied, handle accordingly
            }
        }
    }
    }
*/
}