package com.nexters.android.pliary.view.splash

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.FragmentMainBinding
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.di.annotation.ViewModelKey
import com.nexters.android.pliary.view.main.MainActivity
import com.nexters.android.pliary.viewmodel.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module
internal interface SplashFragmentModule {

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}