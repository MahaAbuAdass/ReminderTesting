package com.example.remindertestapp.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.remindertestapp.MainActivity
import com.example.remindertestapp.R
import com.example.remindertestapp.databinding.VerificationBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class VerificationFragment : BaseFragment() {
    private var binding : VerificationBinding ?=null
    private val navArgs by navArgs<VerificationFragmentArgs>()
     override val mainActivity: MainActivity get() = (activity as MainActivity)


    private lateinit var verificationId: String

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VerificationBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.btnVerify?.setOnClickListener {
            val text1 = binding?.etDigit1.toString()
            val text2 = binding?.etDigit2.toString()
            val text3 = binding?.etDigit3.toString()
            val text4 = binding?.etDigit4.toString()
            val text5 = binding?.etDigit5.toString()
            val text6 = binding?.etDigit6.toString()
            val verificationCode = "$text1 $text2 $text3 $text4 $text5 $text6"
            verifyOtp(verificationCode)
        }
    }
        private fun verifyOtp(otp: String) {
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, otp)

            // Use the credential to sign in to Firebase
            // This is the same method used in the LoginFragment
            // You can reuse the `signInWithPhoneAuthCredential` method here if you prefer
            // I duplicated the code for clarity
            // Replace `this` with `requireActivity()` if you encounter issues
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, navigate to the next screen or perform necessary actions
                        // You can customize this part as per your needs.
                        findNavController().navigate(VerificationFragmentDirections.actionVerificationScreenToNavigationHome())
                    } else {
                        // If sign in fails, display a message to the user.
                        // You can customize this part as per your needs.
                        binding?.error?.text = "Sign In Failed: ${task.exception?.message}"
                    }
                }
        }









//            val options = PhoneAuthOptions.newBuilder(auth)
//                .setPhoneNumber(navArgs.phoneNumber)          // Phone number to verify
//                .setTimeout(60L, TimeUnit.SECONDS)     // Timeout duration
//                .setActivity(mainActivity)                     // Activity (for callback binding)
//                .setCallbacks(callbacks)               // OnVerificationStateChangedCallbacks
//                .build()
//
//            PhoneAuthProvider.verifyPhoneNumber(options)
//                binding?.error?.text = "success 234343434"
//
//            }
//
//        // Initialize callbacks
//        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                // This callback will be invoked in two situations:
//                // 1. Instant verification. In some cases, the phone number can be instantly verified without needing to send or enter a verification code.
//                // 2. Auto-retrieval. On some devices, Google Play services can automatically detect the incoming verification SMS and perform verification without user action.
//                // You can use credential to sign in the user.
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                // This callback is invoked when an invalid request for verification is made, for example, the phone number format is not valid.
//                Toast.makeText(mainActivity, e.toString(), Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {

                val text1 = binding?.etDigit1.toString()
                val text2 = binding?.etDigit2.toString()
                val text3 = binding?.etDigit3.toString()
                val text4 = binding?.etDigit4.toString()
                val text5 = binding?.etDigit5.toString()
                val text6 = binding?.etDigit6.toString()

//                val verificationId = verificationId // This should be the value you received in the onCodeSent callback
               val verificationCode = "$text1 $text2 $text3 $text4 $text5 $text6"
//
//                val credential = PhoneAuthProvider.getCredential(verificationId, verificationCode)
//
//
//            // Signing in the user with the credential
//                auth.signInWithCredential(credential)
//                    .addOnCompleteListener(mainActivity) { task ->
//                        if (task.isSuccessful) {
//                            // User signed in successfully
//                            binding?.error?.text = "success"
//
//                            findNavController().navigate(VerificationFragmentDirections.actionVerificationScreenToNavigationHome())
//                        } else {
//                            // Sign in failed
//                            binding?.error?.text = "failed"
//                            Toast.makeText(mainActivity, "Sign in Failed", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                // The SMS verification code has been sent to the provided phone number, we now need to prompt the user to enter the code and then construct a credential by combining the code with a verification ID.
//                // Save the verification ID and resending token so you can use them later
//            }
//        }
//    }
}