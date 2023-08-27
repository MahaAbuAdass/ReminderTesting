package com.example.remindertestapp.ui.account.createaccount

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.databinding.CreateAccountFragmentBinding
import com.example.remindertestapp.ui.ProgressBarLoader
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountFragment : BaseFragment(), OnClickListener {
    private var binding: CreateAccountFragmentBinding? = null
    private val createAccountViewModel by viewModels<CreateAccountViewModel>()
    private var progressBarLoader: ProgressBarLoader? = null

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateAccountFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarLoader = ProgressBarLoader(requireContext())

        initiate()
        initSharedPreferences()
        observeViewModel()
    }

    private fun initSharedPreferences() {
        sharedPreferences = activity?.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        );
    }

    private fun observeViewModel() {

        createAccountViewModel?.signUpResponse?.observe(viewLifecycleOwner) {
            sharedPreferences?.edit()?.let { editor ->
                editor.putString(KEY_NAME, "bearer ${it?.bearerToken}")
                editor.apply()
            }
            val phone = binding?.etPhone.toString()
           // findNavController().navigate(CreateAccountFragmentDirections.actionSignUpToVerificationScreen(phone))

            findNavController().navigate(CreateAccountFragmentDirections.actionSignUpToNavigationHome2())


        }
        createAccountViewModel?.errorResponse?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }
//        createAccountViewModel?.showProgress?.observe(viewLifecycleOwner) {
//            if (it == true)
//                progressBarLoader?.show()
//            else progressBarLoader?.dismiss()
//        }

    }

    private fun initiate() {
        binding?.btnSignin?.setOnClickListener(this)
        binding?.tvSignUp?.setOnClickListener(this)
    }

    private suspend fun callSignUpAPI() {
        createAccountViewModel.callSignUp(
            SignupRequestModel(
                mobileNumber = binding?.phoneNumber?.countryCode?.text.toString() + binding?.phoneNumber?.phoneNumberEtx?.text.toString(),
                userName = binding?.fullName?.fullNameEtx?.text.toString(), "",
                notificationToken = ""
            )
        )
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.btnSignin?.id ->
                CoroutineScope(Dispatchers.Default).launch { callSignUpAPI() }

            binding?.tvSignUp?.id -> findNavController().navigate(CreateAccountFragmentDirections.actionSignUpToSignin())

        }
    }


}