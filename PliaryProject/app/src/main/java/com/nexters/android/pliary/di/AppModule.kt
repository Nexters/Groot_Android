package com.nexters.android.pliary.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.nexters.android.pliary.PliaryApplication
import com.nexters.android.pliary.base.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named
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
    @Named("appContext")
    @Singleton
    fun bindContext(application: Application): Context

    @Binds
    fun bindViewModelFactory(providerFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}