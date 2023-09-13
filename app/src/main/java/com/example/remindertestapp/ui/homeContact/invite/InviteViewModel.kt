package com.example.remindertestapp.ui.homeContact.invite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.remindertestapp.ui.homeContact.contacts.PhoneNumbersResponse
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InviteViewModel :ViewModel() {
    private var retrofitBuilder = RetrofitBuilder()

    private val _getNotExistingContactResponse = MutableLiveData< ArrayList<PhoneNumbersResponse?>?>()
    val getNotExistingContactResponse: LiveData<ArrayList<PhoneNumbersResponse?>?> = _getNotExistingContactResponse

    private val _getNotExistingContactResponseError = MutableLiveData<String?>()
    val getNotExistingContactResponseError: LiveData<String?> = _getNotExistingContactResponseError


    suspend fun getNotExistingUser(auth: String?) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.getNotExistingContact(auth)
                _getNotExistingContactResponse.postValue(response.data)
            } catch (e: Exception) {
                _getNotExistingContactResponseError.postValue(e.message.toString())
            }
        }
    }
}