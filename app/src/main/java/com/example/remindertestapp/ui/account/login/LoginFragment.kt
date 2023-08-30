package com.example.remindertestapp.ui.account.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.databinding.LoginFragmentBinding
import com.example.remindertestapp.ui.ProgressBarLoader
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

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

            val phone = binding?.etPhone.toString()

        //    findNavController().navigate(LoginFragmentDirections.actionSigninToVerificationScreen(phone))

        //   findNavController().navigate(LoginFragmentDirections.actionSigninToMenu())
   //  findNavController().navigate(LoginFragmentDirections.actionSigninToViewPagerContact())
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

    private suspend fun callSigninApi() {
        loginViewModel?.callLoginApi(
            SigninRequestModel(
                binding?.number?.countryCode?.text.toString() + binding?.number?.phoneNumberEtx?.text.toString()           )
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.btnSignin?.id -> checkPhoneNumberRegistered(
                phoneNumber = binding?.number?.countryCode?.text.toString() + binding?.number?.phoneNumberEtx?.text.toString()
            )
//                CoroutineScope(Dispatchers.IO).launch {
//                    if (binding?.etPhone?.isNotEmpty() == true){
//                    checkRegisteredUser()
//
//                    } else Toast.makeText(context,"please fill you phone number to login",Toast.LENGTH_LONG).show()
//                }

            binding?.tvSignUp?.id -> findNavController().navigate(LoginFragmentDirections.actionSigninToSignUp())
        }
    }

    private fun checkRegisteredUser() {
//        val phoneNumber = binding?.number?.phoneNumberEtx?.text.toString() // Replace with the user's phone number
//        val auth = FirebaseAuth.getInstance()
//
//// Attempt to sign in anonymously to check if the user is already registered
//        auth.signInAnonymously()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    // Sign-in was successful, indicating that the user is registered
//                    val user = auth.currentUser
//                    CoroutineScope(Dispatchers.IO).launch {
//                        callSigninApi()
//                    }
//                } else {
//              Toast.makeText(context,"Sign in failed",Toast.LENGTH_LONG).show()
//
//                // An error occurred during sign-in, indicating that the user is not registered
//                    // Handle the case where the user is not registered
//                }
//            }

    }
            private fun checkPhoneNumberRegistered(phoneNumber: String) {
//            auth.fetchSignInMethodsForEmail(phoneNumber)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val signInMethods = task.result?.signInMethods
//                        if (signInMethods != null && signInMethods.contains(PhoneAuthProvider.PHONE_SIGN_IN_METHOD)) {
//                            // Phone number is already registered
//                            // Handle the case here
//                   CoroutineScope(Dispatchers.IO).launch {
//
//                            callSigninApi()
//                   }
//                            println("Phone number is already registered.")
//                        } else {
//                            // Phone number is not registered
//                            // Handle the case here
//                            println("Phone number is not registered.")
//                            binding?.tvError?.text = "errrrrror"
//                        }
//                    } else {
//                        // Handle the error
//                        val exception = task.exception
//                        println("Error checking phone number: ${exception?.message}")
//                        binding?.tvError?.text = "errrrrror222222222222222"
//
//                    }
//                }

            auth.fetchSignInMethodsForEmail(phoneNumber)
                .addOnCompleteListener{request->
                    if(request.isSuccessful){
                        CoroutineScope(Dispatchers.IO).launch {

                            callSigninApi()
                        }
                    }
                        // The user is successfully signed in

                    else{
                        // The user is not successfully signed in
                     binding?.tvError?.text = "errrrrror222222222222222"

            }
        }}

        // Call the function to check if the phone number is registered
//        private fun performPhoneNumberCheck() {
//            val phoneNumber = binding?.number?.countryCode?.text.toString() + binding?.number?.phoneNumberEtx?.text.toString()


            // Replace with the phone number you want to check
     //       checkPhoneNumberRegistered(phoneNumber)

        // Call performPhoneNumberCheck() from wherever appropriate in your code
    fun checkLoggedinUser() {
        if (sharedPreferences?.getString(KEY_NAME, "")?.isNotEmpty() == true) {
        findNavController().navigate(LoginFragmentDirections.actionSigninToNavigationHome())
        }
    }
}


