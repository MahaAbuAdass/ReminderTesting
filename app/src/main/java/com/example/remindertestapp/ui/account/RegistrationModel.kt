package com.example.remindertestapp.ui.account

import com.google.gson.annotations.SerializedName

data class SigninRequestModel(
    @SerializedName
        ("MobileNumber") val mobileNumber: String?
)

data class SigninResponseModel(

    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: RegistrationResponseModel?,
    @SerializedName("error") val error: BaseError?
)

data class RegistrationResponseModel(
    @SerializedName("profileId") val profileId: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("bearerToken") val bearerToken: String?,
    @SerializedName("error") val baseError: BaseError?
)

data class BaseError(
    @SerializedName("errors") val errors: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("detail") val detail: String?,
    @SerializedName("innerException") val innerException: String?
)

data class SignupRequestModel(
    @SerializedName("MobileNumber") val mobileNumber: String?,
    @SerializedName("UserName") val userName: String?,
    @SerializedName("Key") val key: String?,
    @SerializedName("NotificationToken") val notificationToken: String?
)

data class BooleanDataResponse(

    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: LogoutData?,
    @SerializedName("error") val error: BaseError?
)

data class LogoutData(
    val isLogout: Boolean?
)


