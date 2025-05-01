package com.yeferic.ualacity.domain.usecases

import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.data.errormappers.CityRepositoryError
import com.yeferic.ualacity.domain.exceptions.CityRepositoryErrorMapper
import com.yeferic.ualacity.domain.models.CityModel
import com.yeferic.ualacity.domain.models.CoordinatesModel
import com.yeferic.ualacity.domain.repositories.CityRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
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
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchCityByQueryUseCaseTest {
    private lateinit var useCase: SearchCityByQueryUseCase

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
            SearchCityByQueryUseCase(
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
            val prefix = "Florencia"
            val favoriteIds = listOf("1", "3")
            val cities =
                listOf(
                    CityModel(
                        id = 1,
                        name = "Florencia",
                        country = "IT",
                        coordinates = CoordinatesModel(40.4, -3.7),
                    ),
                    CityModel(
                        id = 2,
                        name = "Florencia",
                        country = "COL",
                        coordinates = CoordinatesModel(32.6, -16.9),
                    ),
                )

            coEvery { cityRepositoryMock.getFavoriteCities() } returns favoriteIds
            coEvery { cityRepositoryMock.searchCityByPrefix(prefix) } returns cities

            // When
            val result = useCase.invoke(prefix).single()
            advanceUntilIdle()

            // Then
            coVerify {
                cityRepositoryMock.getFavoriteCities()
                cityRepositoryMock.searchCityByPrefix(prefix)
            }
            assert(result is UseCaseStatus.Success)
            (result as UseCaseStatus.Success).data.let {
                Assertions.assertEquals(2, it.size)
                Assertions.assertEquals(true, it[0].isFavorite)
                Assertions.assertEquals(false, it[1].isFavorite)
            }
        }

    @Test
    fun `should emit Error when the repository returns error`() =
        runTest {
            // Given
            val prefix = "Florencia"
            val error = Exception("Error")
            coEvery { cityRepositoryMock.getFavoriteCities() } throws error
            every { cityRepositoryErrorMapperMock.getError(error) } returns
                CityRepositoryError.DataNotLoadedError

            // When
            val result = useCase.invoke(prefix).single()
            advanceUntilIdle()

            // Then
            coVerify {
                cityRepositoryMock.getFavoriteCities()
                cityRepositoryErrorMapperMock.getError(error)
            }
            assert(result is UseCaseStatus.Error)
            assert(
                (result as UseCaseStatus.Error)
                    .errorEntity is CityRepositoryError.DataNotLoadedError,
            )
        }
}
