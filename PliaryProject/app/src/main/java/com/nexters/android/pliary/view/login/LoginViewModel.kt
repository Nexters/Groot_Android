package com.nexters.android.pliary.view.login

import android.util.Log
import com.nexters.android.pliary.base.BaseViewModel
import com.nexters.android.pliary.di.repository.LoginRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(val repo : LoginRepository) : BaseViewModel() {

    fun reqSignUp(email: String?, uid: String?) {
        email?.let { e ->
            uid?.let { id ->
                if(e.isNotEmpty() && id.isNotEmpty()) {
                    val result = repo.setSignUp(e, id)
                    if(result.isSuccessful) {
                        val body = result.body()
                        when(body?.statusCode) {
                            200 -> Log.d("SIGN_UP", "SUCCESS!!!!!!!!!!")
                            else -> Log.d("SIGN_UP", "ERROR!!!!!!!!!!!!")
                        }
                    }

                }
            }
        }
    }
}