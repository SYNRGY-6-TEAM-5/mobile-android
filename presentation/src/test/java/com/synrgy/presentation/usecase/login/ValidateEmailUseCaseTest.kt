package com.synrgy.presentation.usecase.login

import com.synrgy.presentation.repository.FakeLoginRepository
import com.synrgy.presentation.usecase.login.ValidateEmailUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {
    private lateinit var repository: FakeLoginRepository
    private lateinit var useCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        repository = FakeLoginRepository()
        useCase = ValidateEmailUseCase(repository)
    }

    @Test
    fun `test invoke function if email filled then return true`() = runTest {
        // Given
        val email = "zachrie2005@gmail.com"
        val expected = true

        // When
        val actual = useCase.invoke(email)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email not filled then return false`() = runTest {
        // Given
        val email = ""
        val expected = false

        // When
        val actual = useCase.invoke(email)

        // Then
        Assert.assertEquals(expected, actual)
    }
}