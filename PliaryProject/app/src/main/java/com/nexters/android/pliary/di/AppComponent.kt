package com.nexters.android.pliary.di

import com.nexters.android.pliary.PliaryApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (ActivityModule::class), (AppModule::class)])
interface AppComponent : AndroidInjector<PliaryApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PliaryApplication>()
}