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
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.R
import com.example.remindertestapp.databinding.ContactsBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactFragment : BaseFragment(), OnClickListener {
    private var binding: ContactsBinding? = null

    val phoneNumbersList = arrayListOf<GetExistUsersRequestModel?>()
    private val contactViewModel by viewModels<ContactViewModel>()

    private var permissionLauncher: ActivityResultLauncher<Array<String>>? = null
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

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

                isReadPermissionGranted =
                    it[Manifest.permission.READ_CONTACTS] ?: isReadPermissionGranted

                if (isReadPermissionGranted) {
                    uploadContactsToServer()
                } else {
                    // Handle permission denied
                    Log.e("Permission", "READ_CONTACTS permission denied")
                }
            }
        requestContactPermission()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        initiate()
        initiatSharedPreference()
//        uploadContactsToServer()
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
            binding?.btn?.id -> {

                callGetContactAPI()

            }

        }
    }

    private fun contactAdapter(phoneNumbers: List<PhoneNumbersResponse?>?) {
        val adapter = ContactAdapter(phoneNumbers, scheduleClicked = {

            val customPopup = ScheduleCustomPopup(requireContext())
            customPopup.show()
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
        callGetContactAPI()

    }


    // Hypothetical ContactUploader class for demonstration (replace with your implementation)
    object ContactUploader {
        fun uploadContactToServer(name: String?, phoneNumber: String?) {
            // Implement your logic to upload contact to the server here
            Log.d("ContactUploader", "Uploading contact: Name=$name, Phone=$phoneNumber")
        }
    }


    inner class ScheduleCustomPopup(context: Context) : Dialog(context) {
        private var contactViewModel: ContactViewModel? = null

        private val popupView: View =
            LayoutInflater.from(context).inflate(R.layout.schedule_popup, null)

        private val topic: EditText = popupView.findViewById(R.id.et_topic)
        private val time: EditText = popupView.findViewById(R.id.et_time)
        private val send: Button = popupView.findViewById(R.id.btn_send)

        init {
            setContentView(popupView)
            send.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    contactViewModel?.makeSchedule(
                        ScheduleRequestModel(
                            callTopic = topic.text.toString(),
                            expectedCallTime = time.text.toString(),
                            callTime = "",
                            recievedUserphoneNumber = ""

                        ), sharedPreferences?.getString(KEY_NAME, "") ?: ""
                    )

                }
            }
            observeViewModel2()

        }

        private fun observeViewModel2() {
            contactViewModel?.scheduleResponse?.observe(viewLifecycleOwner) {
                //close popup
                dismiss()


            }
            contactViewModel?.scheduleResponseError?.observe(viewLifecycleOwner) {
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}



