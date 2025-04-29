package com.yeferic.ualacity.domain.usecases

import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.data.errormappers.CityRepositoryError
import com.yeferic.ualacity.domain.exceptions.CityRepositoryErrorMapper
import com.yeferic.ualacity.domain.repositories.CityRepository
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
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
class FetchRemoteDataUseCaseTest {
    private lateinit var useCase: FetchRemoteDataUseCase

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
            FetchRemoteDataUseCase(
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
    fun `should emit Success after successful fetch of cities`() =
        runTest {
            // Given
            coEvery { cityRepositoryMock.fetchRemoteCities() } just Runs

            // When
            val result = useCase.invoke().single()
            advanceUntilIdle()

            // Then
            coVerify {
                cityRepositoryMock.fetchRemoteCities()
            }
            assert(result is UseCaseStatus.Success)
            assert((result as UseCaseStatus.Success).data)
        }

    @Test
    fun `should emit Error when fetch does not load cities`() =
        runTest {
            // Given
            val error = Exception("Error")
            coEvery { cityRepositoryMock.fetchRemoteCities() } throws error
            every { cityRepositoryErrorMapperMock.getError(error) } returns
                CityRepositoryError.DataNotLoadedError

            // When
            val result = useCase.invoke().single()
            advanceUntilIdle()

            // Then
            coVerify {
                cityRepositoryMock.fetchRemoteCities()
                cityRepositoryErrorMapperMock.getError(error)
            }
            assert(result is UseCaseStatus.Error)
            assert(
                (result as UseCaseStatus.Error)
                    .errorEntity is CityRepositoryError.DataNotLoadedError,
            )
        }
}
