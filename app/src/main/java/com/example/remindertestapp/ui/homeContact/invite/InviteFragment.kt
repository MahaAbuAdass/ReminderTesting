package com.example.remindertestapp.ui.homeContact.invite

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remindertestapp.databinding.InviteFragmentBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.homeContact.contacts.ContactAdapter
import com.example.remindertestapp.ui.homeContact.contacts.ContactViewModel
import com.example.remindertestapp.ui.homeContact.contacts.PhoneNumbersResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InviteFragment : BaseFragment(), View.OnClickListener {
    private var binding: InviteFragmentBinding? = null

    private val inviteViewModel by viewModels<InviteViewModel>()


    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InviteFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiate()
        initiatSharedPreference()
        callGetNotExistingContactAPI()
        observeViewModel()


    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    }

    private fun callGetNotExistingContactAPI() {
        CoroutineScope(Dispatchers.IO).launch {

            inviteViewModel?.getNotExistingUser(sharedPreferences?.getString(KEY_NAME, "") ?: "")
        }
    }

    private fun observeViewModel() {
        CoroutineScope(Dispatchers.IO).launch {

            inviteViewModel?.getNotExistingContactResponse?.observe(viewLifecycleOwner) {
                it?.let {
                    notExistingUserAdapter(it)
                }

            }

            inviteViewModel?.getNotExistingContactResponseError?.observe(viewLifecycleOwner) {
                Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initiate() {

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

        }
    }
    private fun notExistingUserAdapter(phoneNumbers: List<PhoneNumbersResponse?>?) {
        val adapter = NotExistingUserAdapter(phoneNumbers)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
    }
}