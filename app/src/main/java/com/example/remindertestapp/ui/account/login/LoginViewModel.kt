package com.example.remindertestapp.ui.account.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.account.BaseError
import com.example.remindertestapp.ui.account.RegistrationResponseModel
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val retrofitBuilder = RetrofitBuilder()


    private val _loginResponse = MutableLiveData<RegistrationResponseModel?>()
    val loginResponse : LiveData<RegistrationResponseModel?> = _loginResponse

    private val _errorResponse = MutableLiveData<BaseError?>()
    val errorResponse : LiveData<BaseError?> = _errorResponse


    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    suspend fun callLoginApi(signinRequestModel: SigninRequestModel){
        viewModelScope.launch(Dispatchers.IO){
            val response = retrofitBuilder.loginUser(signinRequestModel)
            try {
                _loginResponse.postValue(response)
            }catch (e: Exception)
            {
                _errorResponse.postValue(response.baseError)
            }
            finally { // finally execute after try and catch "always executed"
                _showProgress.postValue(false)
            }

        }

    }



}