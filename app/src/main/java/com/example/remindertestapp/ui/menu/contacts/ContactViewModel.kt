package com.example.remindertestapp.ui.menu.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindertestapp.ui.network.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {
    private var retrofitBuilder = RetrofitBuilder()

    private val _getContactsResponse = MutableLiveData< List<PhoneNumbersResponse?>?>()
    val getContactsResponse: LiveData< List<PhoneNumbersResponse?>?> = _getContactsResponse

    private val _getContactsResponseError = MutableLiveData<String?>()
    val getContactsResponseError: LiveData<String?> = _getContactsResponseError


    suspend fun getContacts(auth: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = retrofitBuilder.getContacts(auth)
                _getContactsResponse.postValue(response.phoneNumbers)
            } catch (e: Exception) {
                _getContactsResponseError.postValue(e.message.toString())
            }
        }
    }
}