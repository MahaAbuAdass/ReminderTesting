package com.example.remindertestapp.ui.account.createaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.remindertestapp.databinding.CreateAccountFragmentBinding
import com.example.remindertestapp.ui.ProgressBarLoader
import com.example.remindertestapp.ui.account.SignupRequestModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountFragment : Fragment() , OnClickListener {
    private var binding : CreateAccountFragmentBinding ?=null
    private var createAccountViewModel : CreateAccountViewModel?=null
    private var progressBarLoader: ProgressBarLoader? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=CreateAccountFragmentBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBarLoader = ProgressBarLoader(requireContext())



        initiate()
        observeViewModel()
    }

    private fun observeViewModel() {
        createAccountViewModel = ViewModelProvider(this)[CreateAccountViewModel::class.java]
      createAccountViewModel?.signUpResponse?.observe(viewLifecycleOwner){

      }

        createAccountViewModel?.errorResponse?.observe(viewLifecycleOwner){
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()

        }
        createAccountViewModel?.showProgress?.observe(viewLifecycleOwner) {
            if (it == true)
                progressBarLoader?.show()
            else progressBarLoader?.dismiss()
        }

    }

    private fun initiate() {
        binding?.btnSignin?.setOnClickListener(this)
        binding?.tvSignUp?.setOnClickListener(this)
    }

 private suspend fun callSignUpAPI() {
        createAccountViewModel?.callSignUp(SignupRequestModel(
            binding?.etName.toString() ,
            binding?.etPhone.toString() ,
            "",
            ""
        ))
    }



    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.tvSignUp?.id ->
                CoroutineScope(Dispatchers.IO).launch { callSignUpAPI() }
          //  binding?.btnSignin?.id ->

        }
    }


}