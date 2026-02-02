package com.example.myapp.domain.usacase

import com.example.myapp.domain.model.RegisterField
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.repository.RegisterRepository
import com.example.myapp.domain.usecase.GetRegisterConfigUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetRegisterConfigUseCaseTest {

    private val repository: RegisterRepository = mockk()
    private val useCase = GetRegisterConfigUseCase(repository)

    @Test
    fun `invoke calls repository and returns same emissions`() = runTest {
        // Arrange
        val config: List<List<RegisterField>> = emptyList()
        val expected: Flow<Resource<List<List<RegisterField>>>> = flowOf(
            Resource.Loader(true),
            Resource.Success(config),
            Resource.Loader(false)
        )

        every { repository.getRegisterConfig() } returns expected

        // Act
        val actualEmissions = useCase().toList()

        // Assert
        verify(exactly = 1) { repository.getRegisterConfig() }

        // Assert
        val expectedEmissions = expected.toList()
        assertEquals(expectedEmissions, actualEmissions)
    }
}
