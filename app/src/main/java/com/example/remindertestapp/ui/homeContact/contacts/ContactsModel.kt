package com.example.remindertestapp.ui.homeContact.contacts

import android.os.Parcelable
import com.example.remindertestapp.ui.account.BaseError
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ContactRequestModel(
    @SerializedName("PhoneNumbers") val phoneNumbers: List<GetExistUsersRequestModel?>?
)

data class GetExistUsersRequestModel(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("telephone") val telephone: String?
)

data class GetExistUsersDataResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data:phoneNumbers,
    @SerializedName("error") val error: BaseError?


//
//    @SerializedName("phoneNumbers") val phoneNumbers: List<PhoneNumbersResponse?>?
)

data class phoneNumbers(
    @SerializedName("phoneNumbers") val phoneNumbers:ArrayList<PhoneNumbersResponse?>?
    )


@Parcelize
data class PhoneNumbersResponse(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("telephone") val telephone: String?,
    @SerializedName("isFavorite") val isFavorite: Boolean?,
    @SerializedName("isInMyCotacts") val isInMyCotacts: Boolean?
) : Parcelable


data class ScheduleRequestModel(
    @SerializedName("RecievedUserphoneNumber") val recievedUserphoneNumber: String?,
    @SerializedName("CallTime") val callTime: String?,
    @SerializedName("ExpectedCallTime") val expectedCallTime: String?,
    @SerializedName("CallTopic") val callTopic: String?
)

data class ScheduleResponse(

    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: SubmitScheduleResponse?,
    @SerializedName("error") val error: BaseError?
)

data class SubmitScheduleResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean?,
    @SerializedName("msg") val message: String?
)