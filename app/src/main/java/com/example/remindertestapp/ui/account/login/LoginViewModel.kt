package com.example.remindertestapp.ui.account.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.account.BaseError
import com.example.remindertestapp.ui.account.RegistrationResponseModel
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.account.SigninResponseModel
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val retrofitBuilder = RetrofitBuilder()
    private lateinit var loginResponsess: SigninResponseModel


    private val _loginResponse = MutableLiveData<RegistrationResponseModel?>()
    val loginResponse: LiveData<RegistrationResponseModel?> = _loginResponse

    private val _errorResponse = MutableLiveData<BaseError?>()
    val errorResponse: LiveData<BaseError?> = _errorResponse


    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    suspend fun callLoginApi(signinRequestModel: SigninRequestModel) {
        _showProgress.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                loginResponsess = retrofitBuilder.loginUser(signinRequestModel)
                loginResponsess?.data?.bearerToken?.let {
                    _loginResponse.postValue(loginResponsess?.data)
                } ?: _errorResponse.postValue(loginResponsess?.error)

            } catch (e: Exception) {
                _errorResponse.postValue(loginResponsess?.error)
            } finally { // finally execute after try and catch "always executed"
                _showProgress.postValue(false)
            }

        }

    }


}