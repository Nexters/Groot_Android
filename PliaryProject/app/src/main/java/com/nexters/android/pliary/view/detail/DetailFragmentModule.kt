package com.nexters.android.pliary.view.detail

import androidx.lifecycle.ViewModel
import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.di.annotation.ViewModelKey
import com.nexters.android.pliary.view.detail.diary.viewmodel.DetailDiaryViewModel
import com.nexters.android.pliary.view.detail.diary.adapter.DetailDiaryAdapter
import com.nexters.android.pliary.view.detail.calendar.viewmodel.DetailCalendarViewModel
import com.nexters.android.pliary.view.detail.edit.DiaryEditViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [DetailFragmentModule.ProvideModule::class])
internal interface DetailFragmentModule {

    @Module
    class ProvideModule {
        @Provides
        @FragmentScope
        fun provideDiaryAdapter(): DetailDiaryAdapter {
            return DetailDiaryAdapter()
        }
    }

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


    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(DetailCalendarViewModel::class)
    fun bindDetailCalendarViewModel(detailCalendarViewModel: DetailCalendarViewModel): ViewModel

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(DiaryEditViewModel::class)
    fun bindDiaryEditViewModel(diaryEditViewModel: DiaryEditViewModel): ViewModel
}