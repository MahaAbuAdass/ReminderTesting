package com.example.remindertestapp.ui.ReSchedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.menu.MyInfoData
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReScheduleViewModel : ViewModel() {
    private var retrofitBuilder = RetrofitBuilder()

    private val _reScheduleResponse = MutableLiveData<MyResheduleInfoData?>()
    val reScheduleResponse: LiveData<MyResheduleInfoData?> = _reScheduleResponse

    private val _reScheduleResponseError = MutableLiveData<String?>()
    val reScheduleResponseError: LiveData<String?> = _reScheduleResponseError


    suspend fun reSchedule(auth: String?, reScheduleRequestModel: ReScheduleRequestModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.rescheduleCalls(auth, reScheduleRequestModel)

                _reScheduleResponse.postValue(response.data)
            } catch (e: Exception) {
                _reScheduleResponseError.postValue(e.message.toString())
            }
        }
    }

}