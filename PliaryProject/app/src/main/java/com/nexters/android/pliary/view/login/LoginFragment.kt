package com.nexters.android.pliary.view.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginViewModel>() {

    override fun getModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.run{
            addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        //btnGoogle.setOnClickListener { navigate(R.id.googleLoginFragment) }
        tvGuestLogin.setOnClickListener { navigate(R.id.action_loginFragment_to_homeFragment) }

        initView()

    }

    private fun initView() {
        setGIF()
    }

    private fun setGIF() {
        context?.let {
            Glide.with(it)
                .asGif()
                .load(R.raw.and_home)
                .centerCrop()
                .into(ivHome)
        }
    }
}