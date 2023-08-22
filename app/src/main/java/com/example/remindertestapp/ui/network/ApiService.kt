package com.example.remindertestapp.ui.network

import com.example.remindertestapp.ui.account.RegistrationResponseModel
import com.example.remindertestapp.ui.account.SigninRequestModel
import com.example.remindertestapp.ui.account.SignupRequestModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/Auth/Login")
    fun login(
        @Body signinRequestModel: SigninRequestModel?
    ): RegistrationResponseModel


    @POST()
    fun signup(
        @Body signupRequestModel: SignupRequestModel
    ): RegistrationResponseModel












}