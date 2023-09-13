package com.example.remindertestapp.ui.Schedule.MyTime

import com.example.remindertestapp.ui.account.BaseError
import com.example.remindertestapp.ui.account.RegistrationResponseModel
import com.google.gson.annotations.SerializedName


data class ScheduleRequestModel(
    @SerializedName("RecievedUserphoneNumber") val recievedUserphoneNumber: String,
    @SerializedName("CallTime") val callTime: String,
    @SerializedName("ExpectedCallTime") val expectedCallTime: String,
    @SerializedName("CallTopic") val callTopic: String
)

data class ScheduleResponse(

    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: SubmitScheduleResponse?,
    @SerializedName("error") val error: BaseError?
)

data class SubmitScheduleResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("msg")  val message: String?
)

