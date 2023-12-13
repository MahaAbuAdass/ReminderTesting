package com.example.remindertestapp.ui.menu


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.remindertestapp.databinding.MenuBinding
import com.example.remindertestapp.ui.base_ui.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class MenuFragment : BaseFragment(), OnClickListener {

    private var binding: MenuBinding? = null
    private val logoutViewModel by viewModels<LogoutViewModel>()
    private val userInfoViewModel by viewModels<UserInfoViewModel>()

    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_NAME = "name"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MenuBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initiate()
        initiatSharedPreference()
        callGetUserApi()
        observerViewModel()
    }

    private fun observeViewModel() {

        userInfoViewModel.getInfoResponse.observe(viewLifecycleOwner) {

            binding?.etName?.setText(it?.userName.toString()) ?: ""
            binding?.etPhoneNumber?.setText(it?.phoneNumber.toString()) ?: ""
            binding?.etEmail?.setText(it?.email.toString()) ?: ""
            binding?.etBirthday?.setText(it?.birthday.toString()) ?: ""
            binding?.etGender?.setText(it?.gender.toString()) ?: ""

        }
        userInfoViewModel?.getInfoError?.observe(viewLifecycleOwner) {
            binding?.error?.text = it.toString()
            Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun callGetUserApi() {
        CoroutineScope(Dispatchers.IO).launch {
            userInfoViewModel?.getUserInfoDate(sharedPreferences?.getString(KEY_NAME, "") ?: "")
        }
    }

    private fun observerViewModel() {
        logoutViewModel?.logoutResponse?.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                sharedPreferences?.edit()?.remove(KEY_NAME)?.apply()
                findNavController().navigate(MenuFragmentDirections.actionMenuToSignin())

            }
        }
        logoutViewModel?.logoutResponseError?.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                binding?.error?.text = it.toString()
                //    Toast.makeText(activity,it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callLogout() {
        CoroutineScope(Dispatchers.IO).launch {
            logoutViewModel?.logoutUser(
                sharedPreferences?.getString(KEY_NAME, "") ?: ""
            )
        }
    }

    private fun initiatSharedPreference() {
        sharedPreferences = activity?.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }

    private fun initiate() {
        binding?.tvLogout?.setOnClickListener(this)
        binding?.back?.setOnClickListener(this)
        binding?.tvChangeLanguage?.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding?.tvLogout?.id -> callLogout()
            binding?.back?.id -> mainActivity.onBackPressed()
            binding?.tvSave?.id -> saveUserInfo()
            binding?.tvChangeLanguage?.id -> {
                changeLanguage()
//                setLocale("ar", requireContext())
//                recreate(mainActivity)
            }
        }
    }

    private fun saveUserInfo() {

    }

    private fun changeLanguage(){
        val currentLanguage = getCurrentLanguage()

        if (currentLanguage == "en") {
            setLocale("ar") //
        } else {
            setLocale("en") // Switch to English
        }

        recreate(mainActivity) // Recreate the activity to apply changes

    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)

        // Save selected language to preferences if needed

        val sharedPrefs = activity?.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        val editor = sharedPrefs?.edit()
        editor?.putString("Language", languageCode)
        editor?.apply()
    }

    private fun getCurrentLanguage(): String {
        val sharedPrefs = activity?.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        );
        return sharedPrefs?.getString("Language", "en") ?: "en"
    }
}



