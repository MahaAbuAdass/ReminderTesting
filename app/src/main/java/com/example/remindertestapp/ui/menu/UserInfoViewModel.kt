package com.example.remindertestapp.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModel() {
    private var retrofitBuilder = RetrofitBuilder()

    private val _getInfoResponse = MutableLiveData<MyInfoData?>()
    val getInfoResponse: LiveData<MyInfoData?> = _getInfoResponse



    private val _getInfoError = MutableLiveData<String?>()
    val getInfoError: LiveData<String?> = _getInfoError


    suspend fun getUserInfoDate(auth: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = retrofitBuilder.getUserInfo(auth)
            try {
                _getInfoResponse.postValue(response.data)
            } catch (e: Exception) {
                _getInfoError.postValue(e.message)
            }
        }
    }


}