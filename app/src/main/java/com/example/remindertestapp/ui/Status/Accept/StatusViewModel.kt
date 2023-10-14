package com.example.remindertestapp.ui.Status.Accept

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatusViewModel : ViewModel() {
    private var retrofitBuilder = RetrofitBuilder()

    private val _acceptScheduleResponse = MutableLiveData<AcceptSchedule?>()
    val acceptScheduleResponse: LiveData<AcceptSchedule?> = _acceptScheduleResponse

    private val _acceptScheduleResponseError = MutableLiveData<String?>()
    val acceptScheduleResponseError: LiveData<String?> = _acceptScheduleResponseError




    private val _cancelScheduleResponse = MutableLiveData<CancelSchedule?>()
    val cancelScheduleResponse: LiveData<CancelSchedule?> = _cancelScheduleResponse

    private val _cancelScheduleResponseError = MutableLiveData<String?>()
    val cancelScheduleResponseError: LiveData<String?> = _cancelScheduleResponseError




    suspend fun acceptSchedule(auth: String, acceptScheduleRequest: AcceptScheduleRequest) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.acceptSchedule(auth,acceptScheduleRequest)
                _acceptScheduleResponse.postValue(response.data)
            } catch (e: Exception) {
                _acceptScheduleResponseError.postValue(e.message.toString())
            }
        }
    }



    suspend fun cancelSchedule(auth: String?,id: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.cancelSchedule(auth,id)
                _cancelScheduleResponse.postValue(response.data)
            } catch (e: Exception) {
                _cancelScheduleResponseError.postValue(e.message.toString())
            }
        }
    }


}