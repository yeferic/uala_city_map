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
class RemoveCityAsFavoriteUseCaseTest {
    private lateinit var useCase: RemoveCityAsFavoriteUseCase

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
            RemoveCityAsFavoriteUseCase(
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
    fun `should emit Success after remove city from favorites`() =
        runTest {
            // Given
            val cityId = "1"
            coEvery { cityRepositoryMock.removeFavoriteCity(cityId) } just Runs

            // When
            val result = useCase.invoke(cityId).single()
            advanceUntilIdle()

            // Then
            coVerify {
                cityRepositoryMock.removeFavoriteCity(cityId)
            }
            assert(result is UseCaseStatus.Success)
            assert((result as UseCaseStatus.Success).data)
        }

    @Test
    fun `should emit Error when the repository returns error`() =
        runTest {
            // Given
            val cityId = "1"
            val error = Exception("Error")
            coEvery { cityRepositoryMock.removeFavoriteCity(cityId) } throws error
            every { cityRepositoryErrorMapperMock.getError(error) } returns
                CityRepositoryError.DataNotLoadedError

            // When
            val result = useCase.invoke(cityId).single()
            advanceUntilIdle()

            // Then
            coVerify {
                cityRepositoryMock.removeFavoriteCity(cityId)
                cityRepositoryErrorMapperMock.getError(error)
            }
            assert(result is UseCaseStatus.Error)
            assert(
                (result as UseCaseStatus.Error)
                    .errorEntity is CityRepositoryError.DataNotLoadedError,
            )
        }
}
