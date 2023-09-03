package com.example.remindertestapp.ui.homeContact

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.core.content.ContextCompat

import androidx.navigation.fragment.findNavController
//import com.example.remindertestapp.Manifest
import com.example.remindertestapp.databinding.ContactViewPagerBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.contacts.ContactFragment
import com.example.remindertestapp.ui.homeContact.contacts.PhoneNumbers
import com.example.remindertestapp.ui.homeContact.invite.InviteFragment
import com.google.android.material.tabs.TabLayoutMediator

class ContactViewPager : BaseFragment(), View.OnClickListener {
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




            if (isReadPermissionGranted) {
                uploadContactsToServer()
            } else {
                // Handle permission denied
                Log.e("Permission", "READ_CONTACTS permission denied")
            }






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
            binding?.imgMenu?.id -> findNavController().navigate(ContactViewPagerDirections.actionViewPagerContactToMenu())
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



    @SuppressLint("Range")
    private fun uploadContactsToServer() {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val phoneNumbersList = mutableListOf<PhoneNumbers?>()

        cursor?.use {

            while (it.moveToNext()) {
                val name =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))



                val phoneNumber =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                phoneNumbersList.add(PhoneNumbers(firstName = name , telephone = phoneNumber)  )

                // Replace this with your server upload logic
                ContactUploader.uploadContactToServer(name, phoneNumber)
            }
        }

        cursor?.close()
    }

    // Hypothetical ContactUploader class for demonstration (replace with your implementation)
    object ContactUploader {
        fun uploadContactToServer(name: String?, phoneNumber: String?) {
            // Implement your logic to upload contact to the server here
            Log.d("ContactUploader", "Uploading contact: Name=$name, Phone=$phoneNumber")
        }
    }



}