package com.example.remindertestapp.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {
    val isMenuOpen: MutableLiveData<Boolean> = MutableLiveData(false)

    fun toggleMenu() {
        isMenuOpen.value = isMenuOpen.value?.not()
    }
}