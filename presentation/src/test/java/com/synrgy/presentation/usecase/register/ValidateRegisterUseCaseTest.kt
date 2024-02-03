package com.synrgy.presentation.usecase.register

import com.synrgy.presentation.repository.FakeRegisterRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidateRegisterUseCaseTest {
    private lateinit var repository: FakeRegisterRepository
    private lateinit var useCase: ValidateRegisterUseCase

    @Before
    fun setUp() {
        repository = FakeRegisterRepository()
        useCase = ValidateRegisterUseCase(repository)
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
    fun `test invoke function if email filled, but password not filled then return false`() = runTest {
        // Given
        val email = "zachrie2005@gmail.com"
        val password = ""
        val expected = false

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email not filled, but password filled then return false`() = runTest {
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
    fun `test invoke function if email not valid and password filled then return false`() = runTest {
        // Given
        val email = "zachrie"
        val password = "password"
        val expected = false

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email valid and password length less than 8 then return false`() = runTest {
        // Given
        val email = "zachrie2005@gmail.com"
        val password = "pass"
        val expected = false

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email valid and password not contains special character then return false`() = runTest {
        // Given
        val email = "zachrie2005@gmail.com"
        val password = "password"
        val expected = false

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email valid and password not contains alphanumeric then return false`() = runTest {
        // Given
        val email = "zachrie2005@gmail.com"
        val password = "password@"
        val expected = false

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email valid and password not contains uppercase letter then return false`() = runTest {
        // Given
        val email = "zachrie2005@gmail.com"
        val password = "password@3"
        val expected = false

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if email valid and password valid then return true`() = runTest {
        // Given
        val email = "zachrie2005@gmail.com"
        val password = "Password@3"
        val expected = true

        // When
        val actual = useCase.invoke(email, password)

        // Then
        Assert.assertEquals(expected, actual)
    }
}