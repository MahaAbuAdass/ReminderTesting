package com.example.remindertestapp.ui.Schedule.new2.MeTime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.Schedule.new2.MyTime.MeMyScheduleData
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MeTimeViewModel :ViewModel() {
    private var retrofitBuilder = RetrofitBuilder()

    private val _getCallsResponse = MutableLiveData<List<InformationReceiverResponseModel?>?>()
    val getCallsResponse: LiveData<List<InformationReceiverResponseModel?>?> = _getCallsResponse

    private val _getCallsResponseError = MutableLiveData<String?>()
    val getCallsResponseError: LiveData<String?> = _getCallsResponseError

    suspend fun getReceivedCallPending(auth: String?) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.getReceivedCallPending(auth)
                _getCallsResponse.postValue(response.data)
            } catch (e: Exception) {

                _getCallsResponseError.postValue(e.message.toString())
            }
        }
    }
}