package com.nexters.android.pliary.di

import com.nexters.android.pliary.di.annotation.ActivityScope
import com.nexters.android.pliary.view.main.MainActivity
import com.nexters.android.pliary.view.main.MainActivityModule
import com.nexters.android.pliary.view.splash.SplashActivity
import com.nexters.android.pliary.view.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun getMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(SplashActivityModule::class)])
    abstract fun getSplashActivity(): SplashActivity
}