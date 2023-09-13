package com.example.remindertestapp.ui.network

import com.example.remindertestapp.ui.Schedule.MyTime.ScheduleRequestModel
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.homeContact.contacts.GetExistUsersRequestModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header


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

    suspend fun getContacts(auth: String? ,getExistUsersRequestModel: GetExistUsersRequestModel) =apiService.getExistUsers(auth, getExistUsersRequestModel)
    suspend fun getNotExistingContact(auth: String?)=apiService.getNotExistUsers(auth)


    suspend fun makeSchedule(scheduleRequestModel: ScheduleRequestModel,auth: String? )=apiService.schedule(scheduleRequestModel,auth)
}


