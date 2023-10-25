package com.example.remindertestapp.ui.network


import com.example.remindertestapp.ui.ReSchedule.ReScheduleRequestModel
import com.example.remindertestapp.ui.status.AcceptScheduleRequest
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.homeContact.contacts.ContactRequestModel
import com.example.remindertestapp.ui.homeContact.contacts.ScheduleRequestModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitBuilder {

    //lazy: define heavy variable as lazy to execute it when call it only

    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://newaeon.westeurope.cloudapp.azure.com:8020")   // Replace with the actual API base URL  // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }


    suspend fun loginUser(signinRequestModel: SigninRequestModel?) =
        apiService.login(signinRequestModel)

    suspend fun signUpUser(signupRequestModel: SignupRequestModel) =
        apiService.signup(signupRequestModel)

    suspend fun logoutUser(auth: String?) = apiService.logout(auth)

    suspend fun getUserInfo(auth: String?) = apiService.getUserProfile(auth)

    suspend fun getContacts(auth: String?, getExistUsersRequestModel: ContactRequestModel) =
        apiService.getExistUsers(auth, getExistUsersRequestModel)

    suspend fun getNotExistingContact(auth: String?) = apiService.getNotExistUsers(auth)


    suspend fun makeSchedule(scheduleRequestModel: ScheduleRequestModel, auth: String?) =
        apiService.schedule(scheduleRequestModel, auth)

    suspend fun getCallsToday(auth: String?) = apiService.getCallsToday(auth)

    suspend fun rescheduleCalls(auth: String?, reScheduleRequestModel: ReScheduleRequestModel) =
        apiService.reschedule(auth, reScheduleRequestModel)

    suspend fun acceptSchedule(auth: String?, acceptScheduleRequest: AcceptScheduleRequest) =
        apiService.acceptSchedule(auth, acceptScheduleRequest)

    suspend fun cancelSchedule(auth: String?, id: String) = apiService.cancelSchedule(auth, id)

    suspend fun getUserPendingCalls (auth: String?)=apiService.getUserPendingCalls(auth)

    suspend fun getReceivedCallPending(auth: String?)=apiService.getReceivedCallPending(auth)

    suspend fun getNotification(auth: String?)=apiService.getNotification(auth)

    suspend fun removeAllNotification(auth: String?)=apiService.removeAllNotification(auth)

}


