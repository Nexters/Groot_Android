package com.nexters.android.pliary.di

import android.app.Application
import android.content.Context
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import com.google.gson.Gson
import javax.inject.Singleton
import dagger.Provides
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import android.preference.PreferenceManager
import android.content.SharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nexters.android.pliary.PliaryApplication
import dagger.Binds
import dagger.Module
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import com.nexters.android.pliary.BuildConfig
import com.nexters.android.pliary.network.LoginService
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit


@Module(includes = [NetModule.ProvideModule::class])
internal interface NetModule {

    companion object {
        const val TIMEOUT = 20L
        const val MAX_RETRY = 3
    }

    @Module
    class ProvideModule {
        @Provides
        @Singleton
        fun providesSharedPreferences(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        @Provides
        @Singleton
        fun provideOkHttpCache(application: Application): Cache {
            val cacheSize = 10 * 1024 * 1024 // 10 MiB
            return Cache(application.cacheDir, cacheSize.toLong())
        }

        @Provides
        @Singleton
        fun provideGson(): Gson {
            return GsonBuilder()
                .setPrettyPrinting()
                .create()
        }

        @Provides
        @Singleton
        fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://groot.devdogs.kr:8080")
                .client(okHttpClient)
                .build()
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })

            if (BuildConfig.DEBUG) {
                okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
            }

            return okHttpClientBuilder.build()
        }

        /*@Provides
        @Singleton
        fun provideJobService() : JobSchedulerStart {
            return JobSchedulerStart
        }*/
    }


}