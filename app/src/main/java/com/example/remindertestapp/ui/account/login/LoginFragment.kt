package com.example.remindertestapp.ui.account.login
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
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
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.firebase.FirebaseApp
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult



class LoginFragment : BaseFragment(), OnClickListener {

    private val customDialog: CustomDialog by lazy { CustomDialog(requireContext()) }


    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private var verificationId: String = ""


    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    private var binding: LoginFragmentBinding? = null
    private var progressBarLoader: ProgressBarLoader? = null
    private val loginViewModel by viewModels<LoginViewModel>()

    var phone22 = "+962${binding?.number?.phoneNumberEtx?.text.toString()}"
    //var number =binding?.number?.countryCode?.text.toString() + binding?.number?.phoneNumberEtx?.text.toString()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FirebaseApp.initializeApp(requireContext())
        auth = FirebaseAuth.getInstance()

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
            }
            val number5 = "+962${binding?.number?.phoneNumberEtx?.text.toString()}"
            startPhoneNumberVerification(number5)

            //findNavController().navigate(LoginFragmentDirections.actionSigninToVerificationScreen(phone22))
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
    }

    private fun callOTP(phoneNumber: String){
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1. Instant verification. In some cases, the phone number can be instantly
                //    verified without needing to send or enter an OTP.
                // 2. Auto-retrieval. On some devices, Google Play services can automatically
                //    detect the incoming verification SMS and perform verification without user action.
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // Failed to send verification code
                Log.e("VerificationFailed", "Failed: ${e.message}")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number.
                this@LoginFragment.verificationId = verificationId
                // Save verification ID and resending token to use later for verifying code
                // through a different button click.
            }
        }
    }


            private fun startPhoneNumberVerification(phoneNumber: String) {
                callOTP(phoneNumber)
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    requireActivity(),
                    callbacks
                )

            }

            private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
                auth.signInWithCredential(credential)
                    .addOnSuccessListener { authResult ->
                        // Handling authentication success
                        val user = authResult.user
                        Log.d("SignIn", "signInWithCredential:success, user: ${user?.uid}")

                        findNavController().navigate(LoginFragmentDirections.actionSigninToVerificationScreen(phone22))

                        // Proceed with your logic after successful authentication
                    }
                    .addOnFailureListener { e ->
                        // Handling authentication failure
                        Log.w("SignIn", "signInWithCredential:failure", e)
                        // Show appropriate message to the user
                    }
                    }


            // Function to verify the entered code (not implemented here)

            // Function to resend verification code (not implemented here)


private fun callLoginAPI() {
    var phone22 = "+962${binding?.number?.phoneNumberEtx?.text.toString()}"

    CoroutineScope(Dispatchers.IO).launch{
        loginViewModel.callLoginApi(
            SigninRequestModel(phone22)
        )
    }
    }


    fun checkFields() {

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


    fun checkLoggedinUser() {
        if (sharedPreferences?.getString(KEY_NAME, "")?.isNotEmpty() == true) {
            findNavController().navigate(LoginFragmentDirections.actionSigninToNavigationHome())
        }
    }


    fun showDialog(text: String) {
        customDialog.showCustomDialog("Login Failed", text)
    }

}







//        val userPhoneNumber = "+962${binding?.number?.phoneNumberEtx?.text.toString()}"
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(userPhoneNumber) // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout duration
//            .setActivity(requireActivity()) // Activity (for callback binding)
//            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)



//    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//
//            val phoneNumber = "+962${binding?.number?.phoneNumberEtx?.text.toString()}"
//            findNavController().navigate(LoginFragmentDirections.actionSigninToVerificationScreen(phoneNumber))
//
//            // This callback will be invoked in two situations:
//            // 1. Instant verification: In some cases, the phone number can be instantly verified without needing to send or enter an OTP.
//            // 2. Auto-retrieval: On some devices, Google Play services can automatically detect the incoming verification SMS and perform verification without user action.
//            Log.d(TAG, "onVerificationCompleted:$credential")
//        }
//
//        override fun onVerificationFailed(e: FirebaseException) {
//            // This callback is invoked if an invalid request is made for verification or if an unsupported request for verification is made.
//            if (e is FirebaseAuthInvalidCredentialsException) {
//                // Invalid request
//                // ...
//            } else if (e is FirebaseTooManyRequestsException) {
//                // The SMS quota for the project has been exceeded
//                // ...
//            }
//            Log.w(TAG, "onVerificationFailed", e)
//        }
//
//        override fun onCodeSent(
//            verificationId: String,
//            token: PhoneAuthProvider.ForceResendingToken
//        ) {
//            // The SMS verification code has been sent to the provided phone number, we can use this code to verify the user.
//            // Save the verification ID and resending token to use later for verifying the code entered by the user
//            Log.d(TAG, "onCodeSent:$verificationId")
//            // Save these values to be used later for verification
//            // verificationId: This value will be used in the verification step
//            // token: This token can be used to resend the verification code
//            // Store these values in a secure place or pass them to the fragment where you'll handle the verification code entry
//        }
//    }
