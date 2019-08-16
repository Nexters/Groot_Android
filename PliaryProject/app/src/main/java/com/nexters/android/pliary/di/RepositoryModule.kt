package com.nexters.android.pliary.di

import com.nexters.android.pliary.di.repository.LocalRepository
import com.nexters.android.pliary.di.repository.LocalRepositoryImp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface RepositoryModule {
    @Binds
    @Singleton
    fun bindLocalDataRepository(localRepository: LocalRepositoryImp): LocalRepository


}