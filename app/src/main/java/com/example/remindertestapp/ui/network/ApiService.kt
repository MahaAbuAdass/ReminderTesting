package com.example.remindertestapp.ui.network

import com.example.remindertestapp.ui.Norification.NotificationResponse
import com.example.remindertestapp.ui.Norification.RemoveAllNotificationResponse
import com.example.remindertestapp.ui.ReSchedule.ReScheduleRequestModel
import com.example.remindertestapp.ui.ReSchedule.RescheduleResponseModel
import com.example.remindertestapp.ui.Schedule.new2.meTime.MeScheduleResponse
import com.example.remindertestapp.ui.Schedule.new2.myTime.MyScheduleResponse
import com.example.remindertestapp.ui.status.AcceptScheduleRequest
import com.example.remindertestapp.ui.status.AcceptScheduleResponse
import com.example.remindertestapp.ui.status.CancelScheduleResponse
import com.example.remindertestapp.ui.account.BooleanDataResponse
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.account.SigninResponseModel
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.dashboardDailyCalls.CallsTodayResponseModel
import com.example.remindertestapp.ui.homeContact.contacts.ContactRequestModel
import com.example.remindertestapp.ui.homeContact.contacts.GetExistUsersDataResponse
import com.example.remindertestapp.ui.homeContact.contacts.ScheduleRequestModel
import com.example.remindertestapp.ui.homeContact.contacts.ScheduleResponse
import com.example.remindertestapp.ui.menu.MyInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

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
        @Body getExistUsersRequestModel: ContactRequestModel
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


    @GET("/api/Reminder/GetCallsToday")
    suspend fun getCallsToday(
        @Header("Authorization") auth: String?

    ): CallsTodayResponseModel


    @POST("/api/Reminder/ReScheduleCall")
    suspend fun reschedule(
        @Header("Authorization") auth: String?,
        @Body reScheduleRequestModel: ReScheduleRequestModel
    ): RescheduleResponseModel


    @POST("/api/Reminder/AcceptSchedule")
    suspend fun acceptSchedule(
        @Header("Authorization") auth: String?,
        @Body acceptScheduleRequest: AcceptScheduleRequest
    ): AcceptScheduleResponse

    @GET("/api/Reminder/CancelScheduleCall")
    suspend fun cancelSchedule(
        @Header("Authorization") auth: String?,
        @Query("Id") id: String
    ): CancelScheduleResponse


    @GET("/api/Reminder/GetUserPendingCalls")
    suspend fun getUserPendingCalls(
         @Header("Authorization") auth: String?
    ) : MyScheduleResponse

    @GET("/api/Reminder/GetCallsAndStatus")
    suspend fun getReceivedCallPending(
        @Header("Authorization") auth: String?
        ) : MeScheduleResponse


    @GET("/api/Reminder/InformReceiver")
   suspend fun informReceiver(
    )


    @GET("/api/Notification/Get")
    suspend fun getNotification(
        @Header("Authorization") auth: String?
    ): NotificationResponse


    @POST("/api/Notification/RemoveSingleNotification")
  suspend  fun removeSingleNotification(
        @Query("notificationID") notificationID: Int?,
        @Header("Authorization") auth: String?
        ): RemoveAllNotificationResponse



    @POST("/api/Notification/HandleNotificationAndCalls")
   suspend fun removeAllNotification(
        @Header("Authorization") auth: String?
    ): RemoveAllNotificationResponse



}