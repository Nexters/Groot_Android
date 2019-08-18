package com.nexters.android.pliary.di.repository

import android.content.SharedPreferences
import com.nexters.android.pliary.network.LoginService
import com.nexters.android.pliary.network.ReqSignUp
import com.nexters.android.pliary.network.ResSignUp
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val retrofit: Retrofit) : LoginRepository {


    override fun setSignUp(email: String, uid: String): Response<ResSignUp> {
        return retrofit.create(LoginService::class.java).signup(ReqSignUp(email, uid))
    }
}