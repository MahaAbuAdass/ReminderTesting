package com.example.remindertestapp.ui.network

import com.example.remindertestapp.ui.account.BooleanDataResponse
import com.example.remindertestapp.ui.account.RegistrationResponseModel
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.account.SignupRequestModel
import com.example.remindertestapp.ui.menu.MyInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("/api/Auth/Login")
    suspend fun login(
        @Body signinRequestModel: SigninRequestModel?
    ): RegistrationResponseModel


    @POST("/api/Auth/Login")
    suspend fun signup(
        @Body signupRequestModel: SignupRequestModel
    ): RegistrationResponseModel


    @POST("/api/Auth/Logout")
    suspend fun logout(
      @Header("Authorization") auth: String?
    ): BooleanDataResponse

    @GET("/api/User/GetUserProfile")
  suspend fun getUserProfile(
        @Header("Authorization") auth: String?
    ): MyInfoResponse


}