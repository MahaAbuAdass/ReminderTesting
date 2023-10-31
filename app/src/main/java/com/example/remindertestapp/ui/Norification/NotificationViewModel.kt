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
import retrofit2.http.Header

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



    private val _removeSingleNotificationResponse = MutableLiveData<RemoveAllNotificationResponseModel?>()
    val removeSingleNotificationResponse : LiveData<RemoveAllNotificationResponseModel?> = _removeSingleNotificationResponse


    private val _clearSingleNotificationResponseError = MutableLiveData<BaseError2?>()
    val clearSingleNotificationResponseError: LiveData<BaseError2?> = _clearSingleNotificationResponseError


    fun getNotification(auth: String?) {
        viewModelScope.launch(Dispatchers.IO) {

            val response = retrofitBuilder.getNotification(auth)

            try {
                _notificationResponse.postValue(response.data?.notifications)
            } catch (e: Exception) {
                _notificationResponseError.postValue(response.error)
            }


        }
    }

    fun removeAllNotification(auth: String , source : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retrofitBuilder.removeAllNotification(auth,source)

            try {
                _clearAllNotificationResponse.postValue(response.data)
            } catch (e: Exception) {
              //  _clearAllNotificationResponseError.postValue(response.error)
            }
        }
    }

    fun removeSignleNotification(notificationID: Int,  auth: String ) {
        viewModelScope.launch(Dispatchers.IO){
            val response = retrofitBuilder.removeSignleNotification(notificationID,auth)

            try {
                _removeSingleNotificationResponse.postValue(response.data)
            } catch (e:Exception) {
                _clearSingleNotificationResponseError.postValue(response.error)
            }
        }
    }


}
