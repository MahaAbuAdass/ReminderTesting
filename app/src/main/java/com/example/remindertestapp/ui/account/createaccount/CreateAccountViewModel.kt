package com.example.remindertestapp.ui.account.createaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.account.BaseError
import com.example.remindertestapp.ui.account.RegistrationResponseModel
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountViewModel : ViewModel() {
    private val retrofitBuilder = RetrofitBuilder()


    private val _signUpResponse = MutableLiveData<RegistrationResponseModel??>()
    val signUpResponse: LiveData<RegistrationResponseModel?> = _signUpResponse

    private val _errorResponse = MutableLiveData<BaseError?>()
    val errorResponse: LiveData<BaseError?> = _errorResponse


    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    suspend fun callSignUp(signupRequestModel: SignupRequestModel) {
        _showProgress.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            val response = retrofitBuilder.signUpUser(signupRequestModel)
            try {
                _signUpResponse.postValue(response)
            } catch (e: Exception) {
                _errorResponse.postValue(response.baseError)
            } finally { // finally execute after try and catch "always executed"
                _showProgress.postValue(false)
            }

        }

    }
}