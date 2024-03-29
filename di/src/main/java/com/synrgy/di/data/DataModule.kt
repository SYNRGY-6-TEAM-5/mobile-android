package com.synrgy.di.data

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.synrgy.data.local.DataStoreManager
import com.synrgy.data.local.LocalRepository
import com.synrgy.data.local.room.FlightDatabase
import com.synrgy.data.remote.NodeRepository
import com.synrgy.data.remote.RemoteRepository
import com.synrgy.data.remote.service.NodeService
import com.synrgy.data.remote.service.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideChuckerInterceptor(context: Context): ChuckerInterceptor {
        return ChuckerInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        chuckerInterceptor: ChuckerInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRemoteService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): RemoteService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://backend-java-production-defa.up.railway.app/api/v1/")
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(RemoteService::class.java)
    }

    @Singleton
    @Provides
    fun provideNodeService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): NodeService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://backend-node-production-21bd.up.railway.app/api/")
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NodeService::class.java)
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

    @Singleton
    @Provides
    fun provideNodeRepository(
        nodeService: NodeService
    ): NodeRepository {
        return NodeRepository(nodeService)
    }

    @Singleton
    @Provides
    fun provideFlightDatabase(
        context: Context
    ): FlightDatabase {
        return Room.databaseBuilder(
            context = context,
            name = FlightDatabase.DATABASE_NAME,
            klass = FlightDatabase::class.java,
        ).build()
    }
}
