package com.nexters.android.pliary.view.main

import com.nexters.android.pliary.di.annotation.FragmentScope
import com.nexters.android.pliary.view.add.AddFragment
import com.nexters.android.pliary.view.add.AddFragmentModule
import com.nexters.android.pliary.view.detail.top.DetailFragment
import com.nexters.android.pliary.view.detail.DetailFragmentModule
import com.nexters.android.pliary.view.detail.bottom.fragment.DetailBottomFragment
import com.nexters.android.pliary.view.detail.bottom.fragment.DetailRoot1Fragment
import com.nexters.android.pliary.view.detail.bottom.fragment.DetailRoot2Fragment
import com.nexters.android.pliary.view.detail.calendar.fragment.DetailCalendarFragment
import com.nexters.android.pliary.view.detail.diary.fragment.DetailDiaryFragment
import com.nexters.android.pliary.view.detail.edit.DiaryEditFragment
import com.nexters.android.pliary.view.home.HomeFragment
import com.nexters.android.pliary.view.home.HomeFragmentModule
import com.nexters.android.pliary.view.login.LoginFragment
import com.nexters.android.pliary.view.login.LoginFragmentModule
import com.nexters.android.pliary.view.login.signin.GoogleLoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [(MainFragmentModule::class)])
    abstract fun bindMainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [(LoginFragmentModule::class)])
    abstract fun bindLoginFragment(): LoginFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [(LoginFragmentModule::class)])
    abstract fun bindGoogleLoginFragment(): GoogleLoginFragment

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    @FragmentScope
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [AddFragmentModule::class])
    @FragmentScope
    abstract fun bindAddFragment(): AddFragment

    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    @FragmentScope
    abstract fun bindDetailFragment(): DetailFragment

    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    @FragmentScope
    abstract fun bindDetailBottomFragment(): DetailBottomFragment


    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    @FragmentScope
    abstract fun DetailRoot1Fragment(): DetailRoot1Fragment


    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    @FragmentScope
    abstract fun DetailRoot2Fragment(): DetailRoot2Fragment

    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    @FragmentScope
    abstract fun bindDetailDiaryFragment(): DetailDiaryFragment

    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    @FragmentScope
    abstract fun bindDetailCalendarFragment(): DetailCalendarFragment

    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    @FragmentScope
    abstract fun bindDiaryEditFragment(): DiaryEditFragment
}
