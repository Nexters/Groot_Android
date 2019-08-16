package com.nexters.android.pliary.di

import android.app.Application
import com.nexters.android.pliary.PliaryApplication
import com.nexters.android.pliary.view.splash.SplashActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    NetModule::class,
    SplashActivityModule::class,
    ActivityModule::class,
    RepositoryModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<PliaryApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}