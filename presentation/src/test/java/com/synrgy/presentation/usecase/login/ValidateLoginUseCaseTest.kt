package com.synrgy.presentation.usecase.login

import com.synrgy.presentation.repository.FakeLoginRepository
import com.synrgy.presentation.usecase.login.ValidateLoginUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidateLoginUseCaseTest {
    private lateinit var repository: FakeLoginRepository
    private lateinit var useCase: ValidateLoginUseCase

    @Before
    fun setUp() {
        repository = FakeLoginRepository()
        useCase = ValidateLoginUseCase(repository)
    }

    @Test
    fun `test invoke function if email and password filled then return true`() = runTest {
        // Given
        val email = "zachrie2005@gmail.com"
        val password = "password"
        val expected = true

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email and password not filled then return false`() = runTest {
        // Given
        val email = ""
        val password = ""
        val expected = false

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email not filled but password filled then return false`() = runTest {
        // Given
        val email = ""
        val password = "password"
        val expected = false

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email filled but password not filled then return false`() = runTest {
        // Given
        val email = "zachrie2005@gmail.com"
        val password = ""
        val expected = false

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }
}