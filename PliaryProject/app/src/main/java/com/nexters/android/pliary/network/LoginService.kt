package com.nexters.android.pliary.network


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {

    @POST("/auth/signup")
    fun signup(@Body req : ReqSignUp): Response<ResSignUp>
}