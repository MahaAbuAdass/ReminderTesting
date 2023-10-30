package com.example.remindertestapp.ui.Norification

import com.example.remindertestapp.ui.account.BaseError
import com.google.gson.annotations.SerializedName


data class NotificationResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("data") val data: NotificationData?,
    @SerializedName("error") val error: BaseError?
)

data class NotificationData (
    @SerializedName("notifications") val notifications: List<NotificationModel>?,
    )

data class NotificationModel(
    @SerializedName("notificationId") val notificationId: Int,
    @SerializedName("userId") val userId: Int?,
    @SerializedName("userPhoneNumber") val userPhoneNumber: String?,
    @SerializedName("userName") val userName: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("body") val body: String?,
    @SerializedName("notificationDate") val notificationDate: String?,
    @SerializedName("notificationTypeId") val notificationTypeId: Int?,
    @SerializedName("isSeen") val isSeen: Boolean?,
    @SerializedName("notificationToken") val notificationToken: String?,
    @SerializedName("isShownInList") val isShownInList: Boolean?,
    @SerializedName("isSent") val isSent: Boolean?,
    @SerializedName("createdBy") val createdBy: String?,
    @SerializedName("createdOn") val createdOn: String?,
    @SerializedName("modifiedBy") val modifiedBy: String?,
    @SerializedName("modifiedOn") val modifiedOn: String?,
    @SerializedName("callTimeDate") val callTimeDate: String?,
    @SerializedName("senderPhoneNumber") val senderPhoneNumber: String?,
    @SerializedName("isDeleted") val isDeleted: Boolean?,
    @SerializedName("expectedCallTime") val expectedCallTime: String?,
    @SerializedName("callTopic") val callTopic: String?,
    @SerializedName("user") val user: String?
)



data class RemoveAllNotificationResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("notifications") val data: RemoveAllNotificationResponseModel?,
    @SerializedName("error") val error: BaseError?
)

data class RemoveAllNotificationResponseModel(
    @SerializedName("isDelete") val isDelete: Boolean?
)
