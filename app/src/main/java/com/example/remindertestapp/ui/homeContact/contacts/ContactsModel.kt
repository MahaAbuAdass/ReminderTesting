package com.example.remindertestapp.ui.homeContact.contacts

import com.google.gson.annotations.SerializedName


data class GetExistUsersRequestModel(
    @SerializedName("PhoneNumbers") val phoneNumbers: List<PhoneNumbers?>?
)

data class PhoneNumbers(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("telephone") val telephone: String?
)

data class GetExistUsersDataResponse(
    @SerializedName("phoneNumbers") val phoneNumbers: List<PhoneNumbersResponse?>?
)

data class PhoneNumbersResponse(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("telephone") val telephone: String?,
    @SerializedName("isFavorite") val isFavorite: Boolean?,
    @SerializedName("isInMyCotacts") val isInMyCotacts: Boolean?
)
