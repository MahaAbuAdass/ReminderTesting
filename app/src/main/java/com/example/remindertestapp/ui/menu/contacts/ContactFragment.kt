package com.example.remindertestapp.ui.menu.contacts

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.databinding.ContactsBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.menu.MenuViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactFragment : BaseFragment(), OnClickListener {
    private var binding: ContactsBinding? = null
    private var contactViewModel: ContactViewModel? = null

    private val viewModel: MenuViewModel by viewModels()

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

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
        initiate()
        initiatSharedPreference()

        observeViewModel()
        callGetContactAPI()


    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    }

    private fun callGetContactAPI() {
        CoroutineScope(Dispatchers.IO).launch {

            contactViewModel?.getContacts(sharedPreferences?.getString(KEY_NAME, "") ?: "")
        }
    }

    private fun observeViewModel() {
        val contactsViewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        CoroutineScope(Dispatchers.IO).launch {

            contactViewModel?.getContactsResponse?.observe(viewLifecycleOwner) {
                it?.let {
                    contactAdapter(it)
                }

            }

            contactViewModel?.getContactsResponseError?.observe(viewLifecycleOwner) {
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initiate() {
        binding?.imgMenu?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
             binding?.imgMenu?.id -> findNavController().navigate(ContactFragmentDirections.actionContactToMenu())
        }
    }


    private fun contactAdapter(phoneNumbers: List<PhoneNumbersResponse?>?) {
        val adapter = ContactAdapter(phoneNumbers)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
    }
}