package com.nexters.android.pliary.view.main

import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.view.add.AddFragment
import com.nexters.android.pliary.view.add.AddFragmentModule
import com.nexters.android.pliary.view.home.HomeFragment
import com.nexters.android.pliary.view.home.HomeFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [(MainFragmentModule::class)])
    abstract fun bindMainFragment(): MainFragment

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    @FragmentScope
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [AddFragmentModule::class])
    @FragmentScope
    abstract fun bindAddFragment(): AddFragment
}
