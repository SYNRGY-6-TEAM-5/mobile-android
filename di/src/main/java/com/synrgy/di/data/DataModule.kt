package com.synrgy.di.data

import android.content.Context
import com.synrgy.data.local.DataStoreManager
import com.synrgy.data.local.LocalRepository
import com.synrgy.data.remote.RemoteRepository
import com.synrgy.data.remote.service.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return "https://lampah-server.vercel.app/api/v1/"
    }

    @Singleton
    @Provides
    fun provideRemoteService(
        gsonConverterFactory: GsonConverterFactory,
        baseUrl: String
    ): RemoteService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(RemoteService::class.java)
    }

    @Singleton
    @Provides
    fun provideDataStoreManager(
        context: Context,
    ): DataStoreManager {
        return DataStoreManager(context)
    }

    @Singleton
    @Provides
    fun provideLocalRepository(
        dataStoreManager: DataStoreManager
    ): LocalRepository {
        return LocalRepository(dataStoreManager)
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(
        remoteService: RemoteService
    ): RemoteRepository {
        return RemoteRepository(remoteService)
    }
}
