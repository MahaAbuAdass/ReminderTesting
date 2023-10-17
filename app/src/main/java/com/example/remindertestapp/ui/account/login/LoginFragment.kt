package com.example.remindertestapp.ui.account.login

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
import com.example.remindertestapp.databinding.LoginFragmentBinding
import com.example.remindertestapp.ui.ProgressBarLoader
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment(), OnClickListener {

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    val auth = FirebaseAuth.getInstance()

    private var binding: LoginFragmentBinding? = null
    private var loginViewModel: LoginViewModel? = null
    private var progressBarLoader: ProgressBarLoader? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarLoader = ProgressBarLoader(requireContext())

        initiate()
        initSharedPreferences()
        observeViewModel()
        checkLoggedinUser()
    }

    private fun observeViewModel() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        loginViewModel?.loginResponse?.observe(viewLifecycleOwner) {

            sharedPreferences?.edit()?.let { editor ->
                editor.putString(KEY_NAME, "bearer ${it?.bearerToken}")
                editor.apply()
            }
            findNavController().navigate(LoginFragmentDirections.actionSigninToNavigationHome())
        }
        loginViewModel?.errorResponse?.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }

        loginViewModel?.showProgress?.observe(viewLifecycleOwner) {
            if (it == true)
                progressBarLoader?.show()
            else progressBarLoader?.dismiss()
        }
    }

    private fun initSharedPreferences() {
        sharedPreferences = activity?.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        );
    }

    private fun initiate() {
        binding?.btnSignin?.setOnClickListener(this)
        binding?.tvSignUp?.setOnClickListener(this)
    }

    private fun callSigninApi() {
        CoroutineScope(Dispatchers.IO).launch {
            loginViewModel?.callLoginApi(
                SigninRequestModel(
                    binding?.number?.countryCode?.text.toString() + binding?.number?.phoneNumberEtx?.text.toString()
                )
            )
        }

    }

    fun checkFields(){

        if (binding?.number?.phoneNumberEtx?.text.toString().isEmpty())
            Toast.makeText(requireContext(), "Please enter your phone number to login", Toast.LENGTH_SHORT).show()

        else callSigninApi()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.btnSignin?.id -> checkFields()
            binding?.tvSignUp?.id -> findNavController().navigate(LoginFragmentDirections.actionSigninToSignUp())
        }
    }
    fun checkLoggedinUser() {
        if (sharedPreferences?.getString(KEY_NAME, "")?.isNotEmpty() == true) {
            findNavController().navigate(LoginFragmentDirections.actionSigninToNavigationHome())
        }
    }
}


