package com.nexters.android.pliary.view.login

import androidx.lifecycle.ViewModel
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
internal interface LoginFragmentModule {

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}