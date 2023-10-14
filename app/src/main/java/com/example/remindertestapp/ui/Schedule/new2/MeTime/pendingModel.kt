package com.example.remindertestapp.ui.Schedule.new2.MeTime

import android.os.Parcelable
import com.example.remindertestapp.ui.Schedule.new2.MyTime.MeMyScheduleData
import com.example.remindertestapp.ui.account.BaseError
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MeMyScheduleResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: List<InformationReceiverResponseModel?>,
    @SerializedName("error") val baseError: BaseError
) : Parcelable


@Parcelize
data class InformationReceiverResponseModel(
    @SerializedName("userName") val userName: String?,
    @SerializedName("callTopic") val callTopic: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("callTime") val callTime: String?,
    @SerializedName("forWho") val forWho: Int?,
    @SerializedName("scheduleStatus") val scheduleStatus: Int?,
    @SerializedName("reminderID") val reminderID: Int?
): Parcelable