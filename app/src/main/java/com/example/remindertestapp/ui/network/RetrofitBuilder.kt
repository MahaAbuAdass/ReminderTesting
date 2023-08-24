package com.example.remindertestapp.ui.network

import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.menu.contacts.GetExistUsersRequestModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitBuilder {

    //lazy: define heavy variable as lazy to execute it when call it only

    private val apiService : ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://newaeon.westeurope.cloudapp.azure.com:8020")   // Replace with the actual API base URL  // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }



    suspend fun loginUser(signinRequestModel: SigninRequestModel?)=apiService.login(signinRequestModel)

    suspend fun signUpUser(signupRequestModel: SignupRequestModel)=apiService.signup(signupRequestModel)

    suspend fun logoutUser(auth: String?)=apiService.logout(auth)

    suspend fun getUserInfo(auth : String?) =apiService.getUserProfile(auth)
    suspend fun getContacts(auth: String?) =apiService.getExistUsers(auth)


}


