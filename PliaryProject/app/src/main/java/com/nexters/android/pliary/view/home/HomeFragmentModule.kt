package com.nexters.android.pliary.view.home

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.FragmentMainBinding
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.di.annotation.ViewModelKey
import com.nexters.android.pliary.view.home.adapter.HomeCardAdapter
import com.nexters.android.pliary.view.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(includes = [HomeFragmentModule.ProvideModule::class])
internal interface HomeFragmentModule {

    @Module
    class ProvideModule {
        @Provides
        @FragmentScope
        fun provideHomeCardAdapter(): HomeCardAdapter {
            return HomeCardAdapter()
        }
    }

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(splashViewModel: HomeViewModel): ViewModel
}