package com.yeferic.ualacity.domain.usecases

import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.domain.exceptions.CityRepositoryErrorMapper
import com.yeferic.ualacity.domain.repositories.CityRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetLocalDataIsLoadedUseCaseTest {
    private lateinit var useCase: GetLocalDataIsLoadedUseCase

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var cityRepositoryMock: CityRepository

    @MockK
    private lateinit var cityRepositoryErrorMapperMock: CityRepositoryErrorMapper

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        useCase =
            GetLocalDataIsLoadedUseCase(
                cityRepository = cityRepositoryMock,
                cityRepositoryErrorMapper = cityRepositoryErrorMapperMock,
            )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(cityRepositoryMock, cityRepositoryErrorMapperMock)
    }

    @Test
    fun `should emit Success true when data is already loaded locally`() =
        runTest {
            // Given
            val status = true
            coEvery { cityRepositoryMock.isLocalCitiesDataLoaded() } returns status

            // When
            val result = useCase.invoke().single()
            advanceUntilIdle()
            // Then
            coVerify {
                cityRepositoryMock.isLocalCitiesDataLoaded()
            }
            assert(result is UseCaseStatus.Success)
            assert(status == (result as UseCaseStatus.Success).data)
        }

    @Test
    fun `should emit Success false when data is not loaded locally yet`() =
        runTest {
            // Given
            val status = false
            coEvery { cityRepositoryMock.isLocalCitiesDataLoaded() } returns status

            // When
            val result = useCase.invoke().single()
            advanceUntilIdle()
            // Then
            coVerify {
                cityRepositoryMock.isLocalCitiesDataLoaded()
            }
            assert(result is UseCaseStatus.Success)
            assert(status == (result as UseCaseStatus.Success).data)
        }
}
