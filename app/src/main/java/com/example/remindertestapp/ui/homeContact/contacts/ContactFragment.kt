package com.example.remindertestapp.ui.homeContact.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.R
import com.example.remindertestapp.databinding.ContactsBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.ContactViewPagerDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ContactFragment : BaseFragment() {
    private var binding: ContactsBinding? = null

    val phoneNumbersList = arrayListOf<GetExistUsersRequestModel?>()
    private val contactViewModel by viewModels<ContactViewModel>()

    private var permissionLauncher: ActivityResultLauncher<Array<String>>? = null
    private var isReadPermissionGranted = false

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, you can now read contacts
                uploadContactsToServer()
            } else {
                // Permission denied, handle accordingly (e.g., show a message or exit the app)
                // You may want to explain why you need this permission to the user
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContactsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        initiatSharedPreference()
        requestContactsPermission()

    }

    private fun requestContactsPermission() {
        // Check if permission is already granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted, you can now read contacts
            uploadContactsToServer()
        } else {
            // Permission is not granted, request it
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun callGetContactAPI() {

        CoroutineScope(Dispatchers.IO).launch {


            contactViewModel.getContacts(
                sharedPreferences?.getString(KEY_NAME, "") ?: "",
                ContactRequestModel(phoneNumbersList)

            )
        }
    }

    private fun observeViewModel() {

        contactViewModel?.getContactsResponse?.observe(viewLifecycleOwner) {
            it?.let {

                binding?.noContactsTxt?.visibility = View.INVISIBLE
                binding?.noContactsTxt?.isVisible = false
                contactAdapter(it)
            }
        }

        contactViewModel?.getContactsResponseError?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }
        contactViewModel?.scheduleResponse?.observe(viewLifecycleOwner) {
            mainActivity.onBackPressed()

        }
        contactViewModel?.scheduleResponseError?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

        }
    }


    private fun contactAdapter(phoneNumbers: List<PhoneNumbersResponse?>?) {
        val adapter = ContactAdapter(phoneNumbers, scheduleClicked = { numbers ->

            findNavController().navigate(ContactViewPagerDirections.actionNavigationHomeToSchedule(numbers)            )
//            val customPopup = ScheduleCustomPopup(requireContext(),PhoneNumbersResponse)
//            customPopup.show()
        })
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
    }


    private fun requestContactPermission() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

        val permissionRequest: MutableList<String> = ArrayList()
        if (!isReadPermissionGranted) {
            permissionRequest.add(Manifest.permission.READ_CONTACTS)
        }
        if (permissionRequest.isNotEmpty()) {
            permissionLauncher?.launch(permissionRequest.toTypedArray())
        }

    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    @SuppressLint("Range")
    private fun uploadContactsToServer() {
        coroutineScope.launch {
            val contentResolver: ContentResolver = requireContext().contentResolver
            val cursor: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            cursor?.use {
                while (it.moveToNext()) {
                    val name =
                        it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

                    val phoneNumber =
                        it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    phoneNumbersList.add(
                        GetExistUsersRequestModel(
                            firstName = name,
                            telephone = phoneNumber
                        )
                    )

                    // Replace this with your server upload logic
                    ContactUploader.uploadContactToServer(name, phoneNumber)
                }
            }
            cursor?.close()
            callGetContactAPI()
            coroutineScope.cancel()
        }
    }


    // Hypothetical ContactUploader class for demonstration (replace with your implementation)
    object ContactUploader {
        fun uploadContactToServer(name: String?, phoneNumber: String?) {
            // Implement your logic to upload contact to the server here
            Log.d("ContactUploader", "Uploading contact: Name=$name, Phone=$phoneNumber")
        }
    }
}






