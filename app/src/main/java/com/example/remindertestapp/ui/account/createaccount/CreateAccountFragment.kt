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
import com.example.remindertestapp.ui.generic.ProgressBarLoader
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.example.remindertestapp.ui.generic.CustomDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class CreateAccountFragment : BaseFragment(), OnClickListener {

    private val customDialog: CustomDialog by lazy { CustomDialog(requireContext()) }

    private var binding: CreateAccountFragmentBinding? = null
    private val createAccountViewModel by viewModels<CreateAccountViewModel>()
    private var progressBarLoader: ProgressBarLoader? = null

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    private lateinit var auth: FirebaseAuth
   private var number = binding?.phoneNumber?.countryCode?.text.toString() + binding?.phoneNumber?.phoneNumberEtx?.text.toString()


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
        auth = FirebaseAuth.getInstance()

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
            val phone = binding?.phoneNumber?.countryCode?.text.toString() +
                    binding?.phoneNumber?.phoneNumberEtx?.text.toString()
            // findNavController().navigate(CreateAccountFragmentDirections.actionSignUpToVerificationScreen(phone))

        //    authenticateWithFirebase(phone)


            findNavController().navigate(CreateAccountFragmentDirections.actionSignUpToNavigationHome2())


        }
        createAccountViewModel?.errorResponse?.observe(viewLifecycleOwner) {
            showDialog(it.toString())
            //Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        }
//        createAccountViewModel?.showProgress?.observe(viewLifecycleOwner) {
//            if (it == true)
//                progressBarLoader?.show()
//            else progressBarLoader?.dismiss()
//        }

    }

//    private fun authenticateWithFirebase(phoneNumber: String) {
//        auth.verifyPhoneNumber(
//            phoneNumber,
//            60L, // Timeout duration
//            TimeUnit.SECONDS,
//            requireActivity(),
//            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                    // This callback will be invoked in two situations:
//                    // 1 - Instant verification. In some cases the phone number can be instantly
//                    //     verified without needing to send or enter a verification code.
//                    // 2 - Auto-retrieval. On some devices Google Play services can automatically
//                    //     detect the incoming verification SMS and perform verification without
//                    //     user action.
//
//                    // Authenticate with Firebase using the credential
//                    auth.signInWithCredential(credential)
//                        .addOnCompleteListener(requireActivity()) { task ->
//                            if (task.isSuccessful) {
//                                // Authentication success
//                                findNavController().navigate(CreateAccountFragmentDirections.actionSignUpToVerificationScreen(number)
//                                )
//                            } else {
//                                // Authentication failed
//                                showDialog("Firebase Authentication Failed")
//                            }
//                        }
//                }
//
//                override fun onVerificationFailed(e: FirebaseException) {
//                    // This callback is invoked if an invalid request for verification is made,
//                    // for instance if the phone number format is not valid.
//                    showDialog("Firebase Verification Failed")
//                }
//
//                // Other callback methods as needed
//            }
//        )
//    }


    private fun initiate() {
        binding?.btnSignUp?.setOnClickListener(this)
        binding?.tvSignUp?.setOnClickListener(this)
    }


     fun checkFields(){

         if (binding?.fullName?.fullNameEtx?.text.toString().isEmpty())
             showDialog("FullName Field is empty")

         //  Toast.makeText(requireContext(), "Please enter your name to sign up", Toast.LENGTH_SHORT).show()

         else    if (binding?.phoneNumber?.phoneNumberEtx?.text.toString().isEmpty())
            showDialog("Phone Number Field is empty")
            //Toast.makeText(requireContext(), "Please enter your phone number to sign up", Toast.LENGTH_SHORT).show()


        else callSignUpAPI()

    }
    private  fun callSignUpAPI() {
        CoroutineScope(Dispatchers.IO).launch {

            createAccountViewModel.callSignUp(
                SignupRequestModel(
                    mobileNumber = binding?.phoneNumber?.countryCode?.text.toString() + binding?.phoneNumber?.phoneNumberEtx?.text.toString(),
                    userName = binding?.fullName?.fullNameEtx?.text.toString(), "",
                    notificationToken = ""
                )
            )
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.btnSignUp?.id ->checkFields()
            binding?.tvSignUp?.id -> findNavController().navigate(CreateAccountFragmentDirections.actionSignUpToSignin())

        }
    }

    fun showDialog(text: String) {
        customDialog.showCustomDialog("Registration Failed", text)
    }


}