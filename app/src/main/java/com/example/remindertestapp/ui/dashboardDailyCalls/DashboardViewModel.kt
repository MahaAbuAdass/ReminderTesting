package com.example.remindertestapp.ui.dashboardDailyCalls

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.account.BaseError
import com.example.remindertestapp.ui.account.RegistrationResponseModel
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val retrofitBuilder = RetrofitBuilder()

    private val _getCallResponse = MutableLiveData<List<DailyCalls>?>()
    val getCallResponse: LiveData<List<DailyCalls>?> = _getCallResponse

    private val _getCallResponseError = MutableLiveData<BaseError?>()
    val getCallResponseError: LiveData<BaseError?> = _getCallResponseError


    suspend fun getDailyCalls(auth: String?) {

        viewModelScope.launch(Dispatchers.IO) {

            val response = retrofitBuilder.getCallsToday(auth)

            try {
                _getCallResponse.postValue(response.data?.allCalls)
            } catch (e: Exception) {
                _getCallResponseError.postValue(response.error)
            }
        }
        }

    }


