package com.example.myapp.domain.usecase

import app.cash.turbine.test
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.model.Tour
import com.example.myapp.domain.repository.TourRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
class GetToursUseCaseTest {

    private val repository: TourRepository = mockk()

    @Test
    fun `invoke returns flow from repository`() = runTest {
        val tours = listOf(
            Tour(
                title = "Tour",
                location = "City",
                number = 1,
                photo = "url",
                price = 100,
                stars = 4
            )
        )

        every { repository.getTours() } returns flowOf(Resource.Success(tours))

        val useCase = GetToursUseCase(repository)

        useCase().test {
            val item = awaitItem() as Resource.Success
            assertEquals(1, item.data.size)
            assertEquals("Tour", item.data.first().title)
            cancelAndIgnoreRemainingEvents()
        }

        verify(exactly = 1) { repository.getTours() }
    }
}
