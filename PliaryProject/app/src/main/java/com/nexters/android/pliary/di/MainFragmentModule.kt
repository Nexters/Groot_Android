package com.nexters.android.pliary.di

import androidx.databinding.DataBindingUtil
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.FragmentMainBinding
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MainFragmentModule {
    @Provides
    @FragmentScope
    fun provideMainFragmentBinding(activity: MainActivity): FragmentMainBinding {
        return DataBindingUtil.inflate<FragmentMainBinding>(
            activity.layoutInflater,
            R.layout.fragment_main,
            null,
            false
        )
    }
}