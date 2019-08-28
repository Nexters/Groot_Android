package com.nexters.android.pliary.view.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.di.annotation.ViewModelKey
import com.nexters.android.pliary.view.home.adapter.HomeCardAdapter
import com.nexters.android.pliary.view.home.holder.PlantCardViewModel
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
        fun provideHomeCardAdapter(plantCardViewModel: PlantCardViewModel): HomeCardAdapter {
            return HomeCardAdapter(plantCardViewModel)
        }
    }

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(PlantCardViewModel::class)
    fun bindPlantCardViewModel(homeViewModel: PlantCardViewModel): ViewModel
}