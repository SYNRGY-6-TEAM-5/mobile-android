package com.synrgy.di.domain

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.repository.NewUserRepository
import com.synrgy.domain.repository.RegisterRepository
import com.synrgy.presentation.usecase.auth.ClearTokenUseCase
import com.synrgy.presentation.usecase.auth.GetNameUseCase
import com.synrgy.presentation.usecase.auth.GetPhotoUseCase
import com.synrgy.presentation.usecase.auth.SetNameUseCase
import com.synrgy.presentation.usecase.auth.SetPhotoUseCase
import com.synrgy.presentation.usecase.login.GetTokenUseCase
import com.synrgy.presentation.usecase.login.LoginUseCase
import com.synrgy.presentation.usecase.login.LoginValidateInputUseCase
import com.synrgy.presentation.usecase.login.SetTokenUseCase
import com.synrgy.presentation.usecase.onboarding.GetNewUserUseCase
import com.synrgy.presentation.usecase.onboarding.SetNewUserUseCase
import com.synrgy.presentation.usecase.register.RegisterUseCase
import com.synrgy.presentation.usecase.register.RegisterValidateInputUseCase
import com.synrgy.presentation.usecase.register.ValidateOtpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideLoginUseCase(
        guestRepository: GuestRepository
    ): LoginUseCase {
        return LoginUseCase(guestRepository)
    }

    @Singleton
    @Provides
    fun provideLoginValidateInputUseCase(
        loginRepository: LoginRepository
    ): LoginValidateInputUseCase {
        return LoginValidateInputUseCase(loginRepository)
    }
    @Singleton
    @Provides
    fun provideRegisterUseCase(
        guestRepository: GuestRepository
    ): RegisterUseCase {
        return RegisterUseCase(guestRepository)
    }

    @Singleton
    @Provides
    fun provideRegisterValidateInputUseCase(
        registerRepository: RegisterRepository
    ): RegisterValidateInputUseCase {
        return RegisterValidateInputUseCase(registerRepository)
    }

    @Singleton
    @Provides
    fun provideValidateOtpUseCase(
        guestRepository: GuestRepository
    ): ValidateOtpUseCase {
        return ValidateOtpUseCase(guestRepository)
    }

    @Singleton
    @Provides
    fun provideSetTokenUseCase(
        loginRepository: LoginRepository
    ): SetTokenUseCase {
        return SetTokenUseCase(loginRepository)
    }

    @Singleton
    @Provides
    fun provideGetTokenUseCase(
        loginRepository: LoginRepository
    ): GetTokenUseCase {
        return GetTokenUseCase(loginRepository)
    }

    @Singleton
    @Provides
    fun provideClearTokenUseCase(
        authRepository: AuthRepository
    ): ClearTokenUseCase {
        return ClearTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideSetNewUserUseCase(
        newUserRepository: NewUserRepository
    ): SetNewUserUseCase {
        return SetNewUserUseCase(newUserRepository)
    }

    @Singleton
    @Provides
    fun provideGetNewUserUseCase(
        newUserRepository: NewUserRepository
    ): GetNewUserUseCase {
        return GetNewUserUseCase(newUserRepository)
    }

    @Singleton
    @Provides
    fun provideSetNameUseCase(
        authRepository: AuthRepository
    ): SetNameUseCase {
        return SetNameUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideGetNameUseCase(
        authRepository: AuthRepository
    ): GetNameUseCase {
        return GetNameUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideSetPhotoUseCase(
        authRepository: AuthRepository
    ): SetPhotoUseCase {
        return SetPhotoUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideGetPhotoUseCase(
        authRepository: AuthRepository
    ): GetPhotoUseCase {
        return GetPhotoUseCase(authRepository)
    }
}