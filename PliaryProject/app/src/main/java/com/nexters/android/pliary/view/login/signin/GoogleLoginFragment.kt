package com.nexters.android.pliary.view.login.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.view.login.LoginViewModel

class GoogleLoginFragment  : BaseFragment<LoginViewModel>() {

    lateinit var auth :FirebaseAuth
    lateinit var authListener : FirebaseAuth.AuthStateListener
    lateinit var googleSignInClient : GoogleSignInClient //구글 로그인을 관리하는 클래스

    override fun getModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_google_login, container, false)

        setGoogleLogin()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                //구글 로그인이 성공했을 경우
                val account = result.signInAccount
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                //account?.let { viewModel.reqSignUp(it.email, it.id) }
            }
        }
    }

    private fun setGoogleLogin() {
        // Configure Google Sign In
        //GoogleSignInOptions 옵션을 관리해주는 클래스로 API 키값과 요청할 값이 저장되어 있다.
        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        context?.let {
            googleSignInClient = GoogleSignIn.getClient(it, gso)
            signIn()
        }

    }

    private fun signIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,100)
    }
}