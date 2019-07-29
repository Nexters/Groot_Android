package com.nexters.android.pliary.di

import android.app.Application
import com.nexters.android.pliary.PliaryApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetModule::class,
    ActivityModule::class,
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