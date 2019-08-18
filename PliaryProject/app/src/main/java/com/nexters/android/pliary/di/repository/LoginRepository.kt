package com.nexters.android.pliary.di.repository

import com.nexters.android.pliary.network.ResSignUp
import retrofit2.Response

interface LoginRepository {
    fun setSignUp(email: String, uid: String) : Response<ResSignUp>
}