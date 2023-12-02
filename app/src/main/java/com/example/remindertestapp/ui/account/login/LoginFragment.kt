package com.example.remindertestapp.ui.account.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.databinding.LoginFragmentBinding
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.generic.CustomDialog
import com.example.remindertestapp.ui.generic.ProgressBarLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment(), OnClickListener {

    private val customDialog: CustomDialog by lazy { CustomDialog(requireContext()) }
    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null
    private var binding: LoginFragmentBinding? = null
    private var progressBarLoader: ProgressBarLoader? = null
    private val loginViewModel by viewModels<LoginViewModel>()
    val phoneNumber get() = "+962${binding?.number?.phoneNumberEtx?.text.toString()}"
    //var number =binding?.number?.countryCode?.text.toString() + binding?.number?.phoneNumberEtx?.text.toString()


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
        checkLoggedinUser()
        observeViewModel()

    }

    private fun observeViewModel() {
        loginViewModel?.loginResponse?.observe(viewLifecycleOwner) {
            sharedPreferences?.edit()?.let { editor ->
                editor.putString(KEY_NAME, "bearer ${it?.bearerToken}")
                editor.apply()
                findNavController().navigate(
                    LoginFragmentDirections.actionSigninToVerificationScreen(
                        phoneNumber
                    )
                )

            }
        }

        loginViewModel?.errorResponse?.observe(viewLifecycleOwner) {
            showDialog(it.toString())

            // Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
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


    private fun callLoginAPI() {

        CoroutineScope(Dispatchers.IO).launch {
            loginViewModel.callLoginApi(
                SigninRequestModel(phoneNumber)
            )
        }
    }


    private fun checkFields() {

        if (binding?.number?.phoneNumberEtx?.text.toString().isEmpty())
            showDialog("Phone number field is empty")
        else {
            callLoginAPI()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.btnSignin?.id -> checkFields()
            binding?.tvSignUp?.id -> findNavController().navigate(LoginFragmentDirections.actionSigninToSignUp())
        }
    }

    private fun checkLoggedinUser() {
        if (sharedPreferences?.getString(KEY_NAME, "")?.isNotEmpty() == true) {
            findNavController().navigate(LoginFragmentDirections.actionSigninToNavigationHome())
        }
    }

    private fun showDialog(text: String) {
        customDialog.showCustomDialog("Login Failed", text)
    }

}
