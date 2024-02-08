package com.synrgy.di.domain

import com.synrgy.data.local.LocalRepository
import com.synrgy.data.remote.NodeRepository
import com.synrgy.data.remote.RemoteRepository
import com.synrgy.domain.repository.AirportRepository
import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.repository.DepartureRepository
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.repository.NewUserRepository
import com.synrgy.domain.repository.RegisterRepository
import com.synrgy.domain.repository.TicketRepository
import com.synrgy.domain.repository.UserRepository
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
    fun provideDepartureRepository(
        remoteRepository: RemoteRepository
    ): DepartureRepository {
        return remoteRepository
    }

    @Singleton
    @Provides
    fun provideAirportRepository(
        remoteRepository: RemoteRepository
    ): AirportRepository {
        return remoteRepository
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        remoteRepository: RemoteRepository
    ): UserRepository {
        return remoteRepository
    }

    @Singleton
    @Provides
    fun provideTicketRepository(
        nodeRepository: NodeRepository
    ): TicketRepository {
        return nodeRepository
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
    fun provideRegisterRepository(
        localRepository: LocalRepository
    ): RegisterRepository {
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