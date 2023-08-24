package com.example.remindertestapp.ui.menu

import com.example.remindertestapp.ui.account.BaseError
import com.google.gson.annotations.SerializedName

data class MyInfoResponse(

    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: MyInfoData?,
    @SerializedName("error") val baseError: BaseError?
)


data class MyInfoData(

    @SerializedName("profileId") val profileId: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("bearerToken") val bearerToken: String?,
    @SerializedName("UserName") val userName: String?,


    @SerializedName("email") val email: String?,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("gender") val gender: String?,



    )


