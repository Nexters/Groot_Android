package com.nexters.android.pliary.network

import com.google.gson.annotations.SerializedName


data class ReqSignUp(
    val email: String,
    val uid: String
)
data class ResSignUp(
    @SerializedName("statusCode")
    val statusCode : Int,
    @SerializedName("statusMsg")
    val statusMsg : String
)