package com.nexters.android.pliary.di

import androidx.databinding.DataBindingUtil
import com.nexters.android.pliary.R
import com.nexters.android.pliary.databinding.ActivityMainBinding
import com.nexters.android.pliary.di.annotation.ActivityScope
import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.view.main.MainActivity
import com.nexters.android.pliary.view.main.MainFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        @ActivityScope
        fun provideMainActivityBinding(activity: MainActivity): ActivityMainBinding {
            return DataBindingUtil.setContentView(activity, R.layout.activity_main)
        }
    }

    @FragmentScope
    @ContributesAndroidInjector(modules = [(MainFragmentModule::class)])
    abstract fun getMainFragment(): MainFragment
}
