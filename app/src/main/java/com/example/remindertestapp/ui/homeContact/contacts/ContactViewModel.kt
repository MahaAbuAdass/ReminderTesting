package com.example.remindertestapp.ui.homeContact.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.account.BaseError

import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {
    private var retrofitBuilder = RetrofitBuilder()

    private val _getContactsResponse = MutableLiveData< List<PhoneNumbersResponse?>?>()
    val getContactsResponse: LiveData< List<PhoneNumbersResponse?>?> = _getContactsResponse

    private val _getContactsResponseError = MutableLiveData<String?>()
    val getContactsResponseError: LiveData<String?> = _getContactsResponseError





    private val _scheduleResponse = MutableLiveData<ScheduleResponse?>()
    val scheduleResponse: LiveData<ScheduleResponse?> = _scheduleResponse

    private val _scheduleResponseError = MutableLiveData<BaseError?>()
    val scheduleResponseError: LiveData<BaseError?> = _scheduleResponseError

    suspend fun getContacts(auth: String?, getExistUsersRequestModel: ContactRequestModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.getContacts(auth,getExistUsersRequestModel)
                _getContactsResponse.postValue(response.data.phoneNumbers)

            } catch (e: Exception) {
                _getContactsResponseError.postValue(e.message.toString())
            }
        }
    }


    suspend fun makeSchedule(scheduleRequestModel: ScheduleRequestModel, auth: String? ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retrofitBuilder.makeSchedule(scheduleRequestModel,auth)

            try {
                _scheduleResponse.postValue(response)
            } catch (e: Exception) {
                _scheduleResponseError.postValue(response.error)
            }
        }
    }


}