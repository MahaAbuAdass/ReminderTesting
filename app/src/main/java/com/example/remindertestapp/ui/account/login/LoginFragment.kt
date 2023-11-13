package com.example.remindertestapp.ui.account.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import java.util.concurrent.TimeUnit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.MainActivity
import com.example.remindertestapp.R
import com.example.remindertestapp.databinding.LoginFragmentBinding
import com.example.remindertestapp.ui.Norification.NotificationViewModel
import com.example.remindertestapp.ui.generic.ProgressBarLoader
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.generic.CustomDialog
import com.example.remindertestapp.ui.homeContact.contacts.phoneNumbers
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment(), OnClickListener {

    private val customDialog: CustomDialog by lazy { CustomDialog(requireContext()) }


    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String


    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null


    private var binding: LoginFragmentBinding? = null

    private val loginViewModel by viewModels<LoginViewModel>()

    private var progressBarLoader: ProgressBarLoader? = null
    val number = binding?.number?.countryCode?.text.toString() + binding?.number?.phoneNumberEtx?.text.toString()


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
        loginViewModel?.loginResponse?.observe(viewLifecycleOwner) {

            sharedPreferences?.edit()?.let { editor ->
                editor.putString(KEY_NAME, "bearer ${it?.bearerToken}")
                editor.apply()
            }

            sendOtp(binding?.number?.countryCode?.text.toString() + binding?.number?.phoneNumberEtx?.text.toString())


//            val options = PhoneAuthOptions.newBuilder(auth)
//                .setPhoneNumber(number)       // Phone number to verify
//                .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Timeout and unit
//                .setActivity(requireActivity())                 // Activity (for callback binding)
//                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
//                .build()
//            PhoneAuthProvider.verifyPhoneNumber(options)



    //   findNavController().navigate(LoginFragmentDirections.actionSigninToNavigationHome())
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

        auth = FirebaseAuth.getInstance()


    }

    private fun callSigninApi() {
        val userPhoneNumber =binding?.number?.countryCode?.text.toString() + binding?.number?.phoneNumberEtx?.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            loginViewModel?.callLoginApi(
                SigninRequestModel(userPhoneNumber)
            )
        }

    }

    fun checkFields() {

        if (binding?.number?.phoneNumberEtx?.text.toString().isEmpty())
            showDialog("Phone number field is empty")
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


    fun showDialog(text: String) {
        customDialog.showCustomDialog("Login Failed", text)
    }




    private fun sendOtp(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // This callback will be invoked in two situations:
                    // 1 - Instant verification. In some cases, the phone number can be instantly
                    //     verified without needing to send or enter a verification code.
                    // 2 - Auto-retrieval. On some devices, Google Play services can automatically
                    //     detect the incoming verification SMS and perform verification without
                    //     user action.
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance, if the phone number format is not valid.
                    // Display error message to the user.
                    // You can customize this part as per your needs.
                    binding?.tvError?.text = "Verification Failed: ${e.message}"
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // Save the verification ID and resending token to use later
                    this@LoginFragment.verificationId = verificationId
                    val action =
                        LoginFragmentDirections.actionSigninToVerificationScreen(
                            verificationId
                        )
                    findNavController().navigate(action)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to the next screen or perform necessary actions
                    // You can customize this part as per your needs.
             //      findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    findNavController().navigate(LoginFragmentDirections.actionSigninToVerificationScreen(number))
                } else {
                    // If sign in fails, display a message to the user.
                    // You can customize this part as per your needs.
                    binding?.tvError?.text = "Sign In Failed: ${task.exception?.message}"
                }
            }
    }



















//    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            // This callback will be invoked in two situations:
//            // 1 - Instant verification. In some cases the phone number can be instantly
//            //     verified without needing to send or enter a verification code.
//            // 2 - Auto-retrieval. On some devices Google Play services can automatically
//            //     detect the incoming verification SMS and perform verification without
//            //     user action.
//            signInWithPhoneAuthCredential(credential)
//        }
//
//        override fun onVerificationFailed(e: FirebaseException) {
//            // This callback is invoked in an invalid request for verification is made,
//            // for instance if the the phone number format is not valid.
//
//            if (e is FirebaseAuthInvalidCredentialsException) {
//                // Invalid request
//                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
//            } else if (e is FirebaseTooManyRequestsException) {
//                // The SMS quota for the project has been exceeded
//                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
//            }
//        //    mProgressBar.visibility = View.VISIBLE
//            // Show a message and update the UI
//        }
//
//        override fun onCodeSent(
//            verificationId: String,
//            token: PhoneAuthProvider.ForceResendingToken
//        ) {
//            // The SMS verification code has been sent to the provided phone number, we
//            // now need to ask the user to enter the code and then construct a credential
//            // by combining the code with a verification ID.
//            // Save verification ID and resending token so we can use them later
//            val intent = Intent(this@LoginFragment , OTPActivity::class.java)
//
//            intent.putExtra("OTP" , verificationId)
//            intent.putExtra("resendToken" , token)
//            intent.putExtra("phoneNumber" , number)
//            startActivity(intent)
//          //  mProgressBar.visibility = View.INVISIBLE
//        }
//    }
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
////                    Toast.makeText(this , "Authenticate Successfully" , Toast.LENGTH_SHORT).show()
//                    sendToMain()
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                    }
//                    // Update UI
//                }
//            //    mProgressBar.visibility = View.INVISIBLE
//            }
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//        if (auth.currentUser != null){
//            startActivity(Intent(this , MainActivity::class.java))
//        }
//    }
    }



