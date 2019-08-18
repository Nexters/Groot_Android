package com.nexters.android.pliary.di

import com.nexters.android.pliary.di.repository.LocalRepository
import com.nexters.android.pliary.di.repository.LocalRepositoryImp
import com.nexters.android.pliary.di.repository.LoginRepository
import com.nexters.android.pliary.di.repository.LoginRepositoryImp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface RepositoryModule {
    @Binds
    @Singleton
    fun bindLocalDataRepository(localRepository: LocalRepositoryImp): LocalRepository

    @Binds
    @Singleton
    fun bindLoginRepository(loginRepository: LoginRepositoryImp): LoginRepository
}