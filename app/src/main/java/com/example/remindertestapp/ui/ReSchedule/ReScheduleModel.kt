package com.example.remindertestapp.ui.ReSchedule

import com.example.remindertestapp.ui.account.BaseError
import com.example.remindertestapp.ui.menu.MyInfoData
import com.google.gson.annotations.SerializedName

data class ReScheduleRequestModel(
    @SerializedName("CallId") val callId: Int?,
    @SerializedName("NewCallTime") val newCallTime: String?
)



data class RescheduleResponseModel(
    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: MyInfoData?,
    @SerializedName("error") val baseError: BaseError?
)

data class MyInfoData(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("msg") val msg: String?
)