package com.synrgy.di.domain

import com.synrgy.domain.repository.AuthRepository
import com.synrgy.domain.repository.DepartureRepository
import com.synrgy.domain.repository.GuestRepository
import com.synrgy.domain.repository.LoginRepository
import com.synrgy.domain.repository.NewUserRepository
import com.synrgy.domain.repository.RegisterRepository
import com.synrgy.presentation.usecase.auth.ClearTokenUseCase
import com.synrgy.presentation.usecase.auth.GetNameUseCase
import com.synrgy.presentation.usecase.auth.GetPhotoUseCase
import com.synrgy.presentation.usecase.auth.GetRegTokenUseCase
import com.synrgy.presentation.usecase.auth.SetNameUseCase
import com.synrgy.presentation.usecase.auth.SetPhotoUseCase
import com.synrgy.presentation.usecase.auth.SetRegTokenUseCase
import com.synrgy.presentation.usecase.departure.GetDepartureUseCase
import com.synrgy.presentation.usecase.forgotpassword.EditPasswordFpUseCase
import com.synrgy.presentation.usecase.forgotpassword.ForgotPasswordUseCase
import com.synrgy.presentation.usecase.forgotpassword.ValidateOtpFpUseCase
import com.synrgy.presentation.usecase.login.GetTokenUseCase
import com.synrgy.presentation.usecase.login.LoginUseCase
import com.synrgy.presentation.usecase.login.LoginValidateInputUseCase
import com.synrgy.presentation.usecase.login.SetTokenUseCase
import com.synrgy.presentation.usecase.onboarding.GetNewUserUseCase
import com.synrgy.presentation.usecase.onboarding.SetNewUserUseCase
import com.synrgy.presentation.usecase.register.RegisterUseCase
import com.synrgy.presentation.usecase.register.RegisterValidateInputUseCase
import com.synrgy.presentation.usecase.register.ValidateOtpUseCase
import com.synrgy.presentation.usecase.user.EditProfileUseCase
import com.synrgy.presentation.usecase.user.UploadProfileImageUseCase
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
    fun provideEditProfileUseCase(
        guestRepository: GuestRepository
    ): EditProfileUseCase {
        return EditProfileUseCase(guestRepository)
    }

    @Singleton
    @Provides
    fun provideUploadProfileImageUseCase(
        guestRepository: GuestRepository
    ): UploadProfileImageUseCase {
        return UploadProfileImageUseCase(guestRepository)
    }

    @Singleton
    @Provides
    fun provideForgotPasswordUseCase(
        guestRepository: GuestRepository
    ): ForgotPasswordUseCase {
        return ForgotPasswordUseCase(guestRepository)
    }

    @Singleton
    @Provides
    fun provideValidateOtpFpUseCase(
        guestRepository: GuestRepository
    ): ValidateOtpFpUseCase {
        return ValidateOtpFpUseCase(guestRepository)
    }

    @Singleton
    @Provides
    fun provideEditPasswordFpUseCase(
        guestRepository: GuestRepository
    ): EditPasswordFpUseCase {
        return EditPasswordFpUseCase(guestRepository)
    }

    @Singleton
    @Provides
    fun provideGetDepartureUseCase(
        departureRepository: DepartureRepository
    ): GetDepartureUseCase {
        return GetDepartureUseCase(departureRepository)
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

    @Singleton
    @Provides
    fun provideSetRegTokenUseCase(
        authRepository: AuthRepository
    ): SetRegTokenUseCase {
        return SetRegTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideGetRegTokenUseCase(
        authRepository: AuthRepository
    ): GetRegTokenUseCase {
        return GetRegTokenUseCase(authRepository)
    }
}