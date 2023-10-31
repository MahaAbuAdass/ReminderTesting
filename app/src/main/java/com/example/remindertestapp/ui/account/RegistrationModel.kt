package com.example.remindertestapp.ui.account

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SigninRequestModel(
    @SerializedName
        ("MobileNumber") val mobileNumber: String?
): Parcelable

@Parcelize
data class SigninResponseModel(

    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: RegistrationResponseModel?,
    @SerializedName("error") val error: BaseError?
): Parcelable


@Parcelize
data class RegistrationResponseModel(
    @SerializedName("profileId") val profileId: String?,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("bearerToken") val bearerToken: String?,
    @SerializedName("error") val baseError: BaseError?
): Parcelable

@Parcelize
data class BaseError(
    @SerializedName("errors") val errors: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("detail") val detail: String?,
    @SerializedName("innerException") val innerException: String?
): Parcelable









@Parcelize
data class SignupRequestModel(
    @SerializedName("MobileNumber") val mobileNumber: String?,
    @SerializedName("UserName") val userName: String?,
    @SerializedName("Key") val key: String?,
    @SerializedName("NotificationToken") val notificationToken: String?
): Parcelable

@Parcelize
data class BooleanDataResponse(

    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: LogoutData?,
    @SerializedName("error") val error: BaseError?
): Parcelable

@Parcelize
data class LogoutData(
    val isLogout: Boolean?
): Parcelable



