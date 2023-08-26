package com.example.remindertestapp.ui.menu

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.databinding.MenuBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuFragment : BaseFragment() , OnClickListener {

    private var binding : MenuBinding?=null
    private var logoutViewModel : LogoutViewModel?=null
    private var userInfoViewModel : UserInfoViewModel?=null

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=MenuBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiate()
        initiatSharedPreference()
        callGetUserApi()

        observerViewModel()
    }

    private fun callGetUserApi() {
        CoroutineScope(Dispatchers.IO).launch {
            userInfoViewModel?.getUserInfoDate(sharedPreferences?.getString(KEY_NAME, "") ?: "")
        }

        userInfoViewModel = ViewModelProvider(this)[UserInfoViewModel::class.java]

        userInfoViewModel?.getInfoResponse?.observe(viewLifecycleOwner){
            CoroutineScope(Dispatchers.Main).launch {

                binding?.etName?.setText(it?.userName.toString())?: ""
                binding?.etPhoneNumber?.setText(it?.phoneNumber.toString())?: ""
                binding?.etEmail?.setText(it?.email.toString())?: ""
                binding?.etBirthday?.setText(it?.birthday.toString())?: ""
                binding?.etGender?.setText(it?.gender.toString())?: ""
            }
        }
        userInfoViewModel?.getInfoError?.observe(viewLifecycleOwner){
            binding?.error?.text=it.toString()
        }
    }

    private fun observerViewModel() {
        logoutViewModel = ViewModelProvider(this)[LogoutViewModel::class.java]

        logoutViewModel?.logoutResponse?.observe(viewLifecycleOwner){
            CoroutineScope(Dispatchers.Main).launch {
                sharedPreferences?.edit()?.remove(KEY_NAME)?.apply()
                findNavController().navigate(MenuFragmentDirections.actionMenuToNavigationHome())

            }
        }
        logoutViewModel?.logoutResponseError?.observe(viewLifecycleOwner){
            CoroutineScope(Dispatchers.Main).launch {
             binding?.error?.text=it.toString()
            //    Toast.makeText(activity,it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callLogout(){
        CoroutineScope(Dispatchers.IO).launch {
            logoutViewModel?.logoutUser(
             sharedPreferences?.getString(KEY_NAME, "") ?: ""
            )
        }
    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun initiate() {
       binding?.tvLogout?.setOnClickListener(this)
        binding?.back?.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.tvLogout?.id -> callLogout()
            binding?.back?.id -> findNavController().navigate(MenuFragmentDirections.actionMenuToViewPagerContact())
        }
    }


}