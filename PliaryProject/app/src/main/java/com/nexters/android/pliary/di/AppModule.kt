package com.nexters.android.pliary.di

import android.app.Application
import android.content.Context
import com.nexters.android.pliary.PliaryApplication
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.ProvideModule::class])
internal interface AppModule {
    @Module
    class ProvideModule {
        @Provides
        @Singleton
        fun provideHelloWorld() = "Hello!!!!!!!!!!"
    }

    @Binds
    @Singleton
    fun bindContext(application: PliaryApplication): Context

}