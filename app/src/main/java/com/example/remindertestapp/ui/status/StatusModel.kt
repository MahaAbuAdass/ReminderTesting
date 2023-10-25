package com.example.remindertestapp.ui.status

import com.example.remindertestapp.ui.account.BaseError
import com.google.gson.annotations.SerializedName

data class AcceptScheduleRequest(
    @SerializedName("ReminderId") val reminderId: Int?
)


data class AcceptScheduleResponse(

    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: AcceptSchedule?,
    @SerializedName("error") val error: BaseError?
)

data class AcceptSchedule(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("msg") val msg: String?
)


data class CancelScheduleResponse(

    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: String?,
    @SerializedName("error") val error: BaseError?
)

//data class CancelSchedule(
//    @SerializedName("msg") val msg: String?
//
//)