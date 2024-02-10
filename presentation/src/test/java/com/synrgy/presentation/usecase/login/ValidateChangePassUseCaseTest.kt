package com.synrgy.presentation.usecase.login

import com.synrgy.presentation.repository.FakeLoginRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ValidateChangePassUseCaseTest {
    private lateinit var repository: FakeLoginRepository
    private lateinit var useCase: ValidateChangePassUseCase

    @Before
    fun setUp() {
        repository = FakeLoginRepository()
        useCase = ValidateChangePassUseCase(repository)
    }

    @Test
    fun `test invoke function if newPassword not filled, but retypePassword filled then return false`() = runTest {
        // Given
        val newPassword = ""
        val retypePassword = "password"
        val expected = false

        // When
        val actual = useCase.invoke(newPassword, retypePassword)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if newPassword filled, but retypePassword not filled then return false`() = runTest {
        // Given
        val newPassword = "password"
        val retypePassword = ""
        val expected = false

        // When
        val actual = useCase.invoke(newPassword, retypePassword)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if newPassword and retypePassword not filled then return false`() = runTest {
        // Given
        val newPassword = ""
        val retypePassword = ""
        val expected = false

        // When
        val actual = useCase.invoke(newPassword, retypePassword)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if newPassword and retypePassword not contains special character then return false`() = runTest {
        // Given
        val newPassword = "password"
        val retypePassword = "password"
        val expected = false

        // When
        val actual = useCase.invoke(newPassword, retypePassword)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if newPassword and retypePassword not contains alphanumeric then return false`() = runTest {
        // Given
        val newPassword = "password@"
        val retypePassword = "password@"
        val expected = false

        // When
        val actual = useCase.invoke(newPassword, retypePassword)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if newPassword and retypePassword not contains uppercase then return false`() = runTest {
        // Given
        val newPassword = "password@3"
        val retypePassword = "password@3"
        val expected = false

        // When
        val actual = useCase.invoke(newPassword, retypePassword)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if newPassword and retypePassword not same then return false`() = runTest {
        // Given
        val newPassword = "Password@3"
        val retypePassword = "Password@4"
        val expected = false

        // When
        val actual = useCase.invoke(newPassword, retypePassword)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test invoke function if newPassword and retypePassword same then return true`() = runTest {
        // Given
        val newPassword = "Password@3"
        val retypePassword = "Password@3"
        val expected = true

        // When
        val actual = useCase.invoke(newPassword, retypePassword)

        // Then
        Assert.assertEquals(expected, actual)
    }
}