package com.example.remindertestapp.ui.Schedule.new2.MyTime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyTimeViewModel : ViewModel() {

    private var retrofitBuilder = RetrofitBuilder()

    private val _scheduleResponse = MutableLiveData<SubmitScheduleResponse?>()
    val scheduleResponse: LiveData<SubmitScheduleResponse?> = _scheduleResponse

    private val _scheduleResponseError = MutableLiveData<String?>()
    val scheduleResponseError: LiveData<String?> = _scheduleResponseError


    suspend fun makeSchedule(scheduleRequestModel: ScheduleRequestModel, auth: String? ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.makeSchedule(scheduleRequestModel,auth)
                _scheduleResponse.postValue(response.data)
            } catch (e: Exception) {
                _scheduleResponseError.postValue(e.message.toString())
            }
        }
    }



}