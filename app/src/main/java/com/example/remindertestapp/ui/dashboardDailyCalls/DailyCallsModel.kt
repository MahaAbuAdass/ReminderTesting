package com.example.remindertestapp.ui.dashboardDailyCalls

import com.google.gson.annotations.SerializedName

data class CallsTodayResponseModel(
    @SerializedName("allCalls") val allCalls: List<DailyCalls>?
)

data class DailyCalls(
    @SerializedName("userName") val userName: String?,
    @SerializedName("userphoneNumber") val userPhoneNumber: String?,
    @SerializedName("callTime") val callTime: String?,
    @SerializedName("expectedCallTime") val expectedCallTime: String?,
    @SerializedName("callTopic") val callTopic: String?,
    @SerializedName("callID") val callID: Int?,
    @SerializedName("callType") val callType: Int?
)