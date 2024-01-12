package com.synrgy.di.domain

import com.synrgy.data.local.LocalRepository
import com.synrgy.data.remote.RemoteRepository
import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.repository.NewUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideGuestRepository(
        remoteRepository: RemoteRepository
    ): GuestRepository {
        return remoteRepository
    }
    @Singleton
    @Provides
    fun provideLoginRepository(
        localRepository: LocalRepository
    ): LoginRepository {
        return localRepository
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        localRepository: LocalRepository
    ): AuthRepository {
        return localRepository
    }

    @Singleton
    @Provides
    fun provideNewUserRepository(
        localRepository: LocalRepository
    ): NewUserRepository {
        return localRepository
    }
}