package com.nexters.android.pliary.view.detail

import androidx.lifecycle.ViewModel
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.di.annotation.ViewModelKey
import com.nexters.android.pliary.view.detail.bottom.DetailDiaryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface DetailFragmentModule {

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun bindDetailViewModel(detailViewModel: DetailViewModel): ViewModel

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(DetailDiaryViewModel::class)
    fun bindDetailDiaryViewModel(detailDiaryViewModel: DetailDiaryViewModel): ViewModel
}