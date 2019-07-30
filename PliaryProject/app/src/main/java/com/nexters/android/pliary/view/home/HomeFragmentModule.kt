package com.nexters.android.pliary.view.home

import androidx.lifecycle.ViewModel
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface HomeFragmentModule {

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(splashViewModel: HomeViewModel): ViewModel
}