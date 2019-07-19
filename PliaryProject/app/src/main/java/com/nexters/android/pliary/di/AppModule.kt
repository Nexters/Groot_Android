package com.nexters.android.pliary.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideHelloWorld() = "Hello World!!"
}