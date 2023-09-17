package com.example.remindertestapp.ui.Schedule.new2.MyTime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.homeContact.contacts.PhoneNumbersResponse
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyTimeViewModel : ViewModel() {

    private var retrofitBuilder = RetrofitBuilder()

    private val _getPendingCallsResponse = MutableLiveData<List<MeMyScheduleData?>??>()
    val getPendingCallsResponse: LiveData< List<MeMyScheduleData?>?> = _getPendingCallsResponse

    private val _getPendingCallsResponseError = MutableLiveData<String?>()
    val getPendingCallsResponseError: LiveData<String?> = _getPendingCallsResponseError

    suspend fun getUserPendingCalls(auth: String?) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.getUserPendingCalls(auth)
                _getPendingCallsResponse.postValue(response.data)
            } catch (e: Exception) {

                _getPendingCallsResponseError.postValue(e.message.toString())
            }
        }
    }

}