package com.example.remindertestapp.ui.Norification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.account.BaseError
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class NotificationViewModel : ViewModel() {
    private val retrofitBuilder = RetrofitBuilder()


    private val _notificationResponse = MutableLiveData<List<NotificationModel>?>()
    val notificationResponse: LiveData<List<NotificationModel>?> = _notificationResponse

    private val _notificationResponseError = MutableLiveData<BaseError?>()
    val notificationResponseError: LiveData<BaseError?> = _notificationResponseError


    private val _clearAllNotificationResponse =
        MutableLiveData<RemoveAllNotificationResponseModel?>()
    val clearAllNotificationResponse: LiveData<RemoveAllNotificationResponseModel?> =
        _clearAllNotificationResponse

    private val _clearAllNotificationResponseError = MutableLiveData<BaseError?>()
    val clearAllNotificationResponseError: LiveData<BaseError?> = _clearAllNotificationResponseError


    fun getNotification(auth: String?) {
        viewModelScope.launch(Dispatchers.IO) {

            val response = retrofitBuilder.getNotification(auth)

            try {
                _notificationResponse.postValue(response.notifications)
            } catch (e: Exception) {
                _notificationResponseError.postValue(response.error)
            }


        }
    }

    fun removeAllNotification(auth: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retrofitBuilder.removeAllNotification(auth)

            try {
                _clearAllNotificationResponse.postValue(response.removeAllNotificationResponseModel)
            } catch (e: Exception) {
                _clearAllNotificationResponseError.postValue(response.error)
            }
        }
    }


}
