package com.example.remindertestapp.ui.network

import com.example.remindertestapp.ui.Schedule.new2.MyTime.ScheduleRequestModel
import com.example.remindertestapp.ui.Schedule.new2.MyTime.ScheduleResponse
import com.example.remindertestapp.ui.account.BooleanDataResponse
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.account.SigninResponseModel
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.homeContact.contacts.GetExistUsersDataResponse
import com.example.remindertestapp.ui.homeContact.contacts.GetExistUsersRequestModel
import com.example.remindertestapp.ui.menu.MyInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("/api/Auth/Login")
    suspend fun login(
        @Body signinRequestModel: SigninRequestModel?
    ): SigninResponseModel


    @POST("/api/Auth/Login")
    suspend fun signup(
        @Body signupRequestModel: SignupRequestModel
    ): SigninResponseModel


    @POST("/api/Auth/Logout")
    suspend fun logout(
        @Header("Authorization") auth: String?
    ): BooleanDataResponse

    @GET("/api/User/GetUserProfile")
    suspend fun getUserProfile(
        @Header("Authorization") auth: String?
    ): MyInfoResponse

    @POST("/api/User/GetExisitUsers")
    suspend fun getExistUsers(
        @Header("Authorization") auth: String?,
        @Body getExistUsersRequestModel: GetExistUsersRequestModel
    ): GetExistUsersDataResponse


    @GET("/api/User/GetNotExisitUsers")
    suspend fun getNotExistUsers(
        @Header("Authorization") auth: String?
    ): GetExistUsersDataResponse

//    @GET("/api/Reminder/getUserPhoneCalls")
//    suspend fun getUserCalls(
//        @Header("Authorization") auth: String?
//    ):  GetUserCallsResponse

    @POST("/api/Reminder/ScheduleCall")
    suspend fun schedule(
        @Body scheduleRequestModel: ScheduleRequestModel,
        @Header("Authorization") auth: String?
    ): ScheduleResponse


}