package com.example.remindertestapp.ui.homeContact.contacts

import android.Manifest
import android.annotation.SuppressLint
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
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.databinding.ContactsBinding
import com.example.remindertestapp.ui.account.createaccount.CreateAccountViewModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactFragment : BaseFragment(), OnClickListener {
    private var binding: ContactsBinding? = null

    val phoneNumbersList = arrayListOf<GetExistUsersRequestModel?>()

    private val contactViewModel by viewModels<ContactViewModel>()

    private  var getExistUsersRequestModel: GetExistUsersRequestModel ?=null

    private var permissionLauncher: ActivityResultLauncher<Array<String>> ?=null
    private var isReadPermissionGranted = false

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContactsBinding.inflate(inflater, container, false)
        requestContactPermission()

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {


                isReadPermissionGranted =
                    it[Manifest.permission.READ_CONTACTS] ?: isReadPermissionGranted

                if (isReadPermissionGranted) {
                    uploadContactsToServer()

                    CoroutineScope(Dispatchers.IO).launch {
                        callGetContactAPI()
                    }
                } else {

                    // uploadContactsToServer()
                    // Handle permission denied
                    Log.e("Permission", "READ_CONTACTS permission denied")
                }
            }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initiate()
        initiatSharedPreference()
        CoroutineScope(Dispatchers.IO).launch {
        callGetContactAPI() }
        observeViewModel()

    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private suspend fun callGetContactAPI() {

        CoroutineScope(Dispatchers.IO).launch {

            getExistUsersRequestModel?.let {
                contactViewModel?.getContacts(
                    sharedPreferences?.getString(KEY_NAME, "") ?: "",
                    it
                )
            }
        }
    }

    private fun observeViewModel() {

        contactViewModel?.getContactsResponse?.observe(viewLifecycleOwner) {
            it?.let {
                contactAdapter(it)
            }
        }

        contactViewModel?.getContactsResponseError?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun initiate() {
        binding?.btn?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.btn?.id ->{
                CoroutineScope(Dispatchers.IO).launch {
                    callGetContactAPI()
                }
            }

        }
    }

    private fun contactAdapter(phoneNumbers: List<PhoneNumbersResponse?>?) {
        val adapter = ContactAdapter(phoneNumbers)
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



        phoneNumbersList

    }



    // Hypothetical ContactUploader class for demonstration (replace with your implementation)
    object ContactUploader {
        fun uploadContactToServer(name: String?, phoneNumber: String?) {
            // Implement your logic to upload contact to the server here
            Log.d("ContactUploader", "Uploading contact: Name=$name, Phone=$phoneNumber")
        }
    }


}