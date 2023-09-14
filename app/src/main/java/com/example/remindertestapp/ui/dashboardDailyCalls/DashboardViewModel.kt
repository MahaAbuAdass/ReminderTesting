package com.example.remindertestapp.ui.dashboardDailyCalls

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remindertestapp.ui.account.BaseError
import com.example.remindertestapp.ui.account.RegistrationResponseModel
import com.example.remindertestapp.ui.network.RetrofitBuilder

class DashboardViewModel : ViewModel() {
    private val retrofitBuilder = RetrofitBuilder()

    private val _getCallResponse = MutableLiveData<RegistrationResponseModel?>()
    val signUpResponse: LiveData<RegistrationResponseModel?> = _signUpResponse

    private val _errorResponse = MutableLiveData<BaseError?>()
    val errorResponse: LiveData<BaseError?> = _errorResponse

}