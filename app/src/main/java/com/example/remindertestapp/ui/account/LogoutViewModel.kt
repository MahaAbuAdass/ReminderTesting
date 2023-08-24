package com.example.remindertestapp.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogoutViewModel : ViewModel() {
    private var retrofitBuilder = RetrofitBuilder()

    private val _logoutResponse = MutableLiveData<Boolean?>()
    val logoutResponse : LiveData<Boolean?> = _logoutResponse

    private val _logoutResponseError = MutableLiveData<String?>()
    val logoutResponseError : LiveData<String?> = _logoutResponseError


    suspend fun logoutUser(auth: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = retrofitBuilder.logoutUser(auth)
                if (response.data == true){
                    _logoutResponse.postValue(true)
                }else
                    _logoutResponseError.postValue("Error : $response")
            } catch (e:Exception){
                _logoutResponseError.postValue("Error Occured : ${e.message}")
            }
        }

    }
}