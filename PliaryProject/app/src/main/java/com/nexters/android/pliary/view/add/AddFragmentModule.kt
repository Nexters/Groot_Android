package com.nexters.android.pliary.view.add

import androidx.lifecycle.ViewModel
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface AddFragmentModule {

    @Binds
    @FragmentScope
    @IntoMap
    @ViewModelKey(AddViewModel::class)
    fun bindAddViewModel(addViewModel: AddViewModel): ViewModel
}