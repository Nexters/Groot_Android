package com.nexters.android.pliary.di

import com.nexters.android.pliary.di.annotation.ActivityScope
import com.nexters.android.pliary.view.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun getMainActivity(): MainActivity
}