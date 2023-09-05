package com.example.remindertestapp.ui.homeContact.contacts

import com.example.remindertestapp.ui.account.BaseError
import com.example.remindertestapp.ui.account.RegistrationResponseModel
import com.google.gson.annotations.SerializedName


data class GetExistUsersRequestModel(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("telephone") val telephone: String?
)

data class GetExistUsersDataResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: List<PhoneNumbersResponse?>?,
    @SerializedName("error") val error: BaseError?


//
//    @SerializedName("phoneNumbers") val phoneNumbers: List<PhoneNumbersResponse?>?
)

data class PhoneNumbersResponse(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("telephone") val telephone: String?,
    @SerializedName("isFavorite") val isFavorite: Boolean?,
    @SerializedName("isInMyCotacts") val isInMyCotacts: Boolean?
)
