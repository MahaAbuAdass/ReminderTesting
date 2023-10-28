package com.example.remindertestapp.ui.Schedule.new2.myTime

import android.os.Parcelable
import com.example.remindertestapp.ui.Schedule.new2.meTime.InformationReceiverResponseModel
import com.example.remindertestapp.ui.account.BaseError
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MyScheduleResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: List<MeMyScheduleData?>? = null,
    @SerializedName("error") val baseError: BaseError
) : Parcelable


@Parcelize
data class MeMyScheduleData(
    @SerializedName("userName") var userName: String? = null,
    @SerializedName("callTopic") var callTopic: String? = null,
    @SerializedName("phoneNumber") var phoneNumber: String? = null,
    @SerializedName("callTime") var callTime: String? = null,
    @SerializedName("expectedCallTime") var expectedCallTime: String? = null,
    @SerializedName("scheduleStatus") var scheduleStatus: Int? = null,
    @SerializedName("canceldBy") var cancelledBy: String? = null,
    @SerializedName("forWho") var forWho: Int? = null,
    @SerializedName("reminderID") var reminderID: Int? = null,
    @SerializedName("isReScheduleByMe") var isReScheduledByMe: Int? = null
) : Parcelable


