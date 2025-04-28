package com.yeferic.ualacity.data.repositories

import com.yeferic.ualacity.data.sources.local.dao.CityDao
import com.yeferic.ualacity.data.sources.local.entities.City
import com.yeferic.ualacity.data.sources.local.entities.Coord
import com.yeferic.ualacity.data.sources.local.entities.mapToDomain
import com.yeferic.ualacity.data.sources.remote.CityApi
import com.yeferic.ualacity.data.sources.remote.dto.CityDTO
import com.yeferic.ualacity.data.sources.remote.dto.CoordinatesDTO
import com.yeferic.ualacity.data.sources.remote.dto.mapToDao
import com.yeferic.ualacity.domain.models.CityModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityRepositoryImplTest {
    private lateinit var repositoryImpl: CityRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var mockCityApi: CityApi

    @MockK
    private lateinit var mockCityDao: CityDao

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        repositoryImpl =
            CityRepositoryImpl(
                ioDispatcher = testDispatcher,
                cityApi = mockCityApi,
                cityDao = mockCityDao,
            )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(mockCityDao, mockCityApi)
    }

    @Test
    fun `fetchRemoteCities inserts cities into DAO`() =
        runTest(testDispatcher) {
            // Given
            val cityResponses =
                listOf(
                    CityDTO(
                        id = 1,
                        name = "City A",
                        country = "Country A",
                        coordinates =
                            CoordinatesDTO(
                                lat = 1.0,
                                lon = 2.0,
                            ),
                    ),
                    CityDTO(
                        id = 1,
                        name = "City B",
                        country = "Country B",
                        coordinates =
                            CoordinatesDTO(
                                lat = 1.0,
                                lon = 2.0,
                            ),
                    ),
                )
            val cityModels = cityResponses.map { it.mapToDao() }
            coEvery { mockCityApi.getCities() } returns cityResponses
            coEvery { mockCityDao.insertCities(cityModels) } just Runs

            // When
            repositoryImpl.fetchRemoteCities()
            advanceUntilIdle()

            // Then
            coVerify {
                mockCityApi.getCities()
                mockCityDao.insertCities(cityModels)
            }
        }

    @Test
    fun `isLocalCitiesDataLoaded should returns true when local data is loaded`() =
        runTest(testDispatcher) {
            // Given
            val isLoaded = true
            val citiesCount = 10
            coEvery { mockCityDao.getCitiesCount() } returns citiesCount

            // When
            val response = repositoryImpl.isLocalCitiesDataLoaded()
            advanceUntilIdle()

            // Then
            coVerify {
                mockCityDao.getCitiesCount()
            }

            assert(response == isLoaded)
        }

    @Test
    fun `isLocalCitiesDataLoaded should returns false when local data is empty`() =
        runTest(testDispatcher) {
            // Given
            val isLoaded = false
            val citiesCount = 0
            coEvery { mockCityDao.getCitiesCount() } returns citiesCount

            // When
            val response = repositoryImpl.isLocalCitiesDataLoaded()
            advanceUntilIdle()

            // Then
            coVerify {
                mockCityDao.getCitiesCount()
            }

            assert(response == isLoaded)
        }

    @Test
    fun `searchCityByPrefix should returns cities when prefix matches any of them`() =
        runTest(testDispatcher) {
            // Given
            val prefix = "Flo"
            val cities =
                listOf(
                    City(
                        id = 1,
                        name = "Florencia",
                        country = "Colombia",
                        coord =
                            Coord(
                                lat = 1.0,
                                lon = 2.0,
                            ),
                    ),
                    City(
                        id = 2,
                        name = "Florencia",
                        country = "Italy",
                        coord =
                            Coord(
                                lat = 1.0,
                                lon = 2.0,
                            ),
                    ),
                )

            val cityModels = cities.map { it.mapToDomain() }

            coEvery { mockCityDao.searchCitiesByPrefix(prefix) } returns cities

            // When
            val response = repositoryImpl.searchCityByPrefix(prefix)
            advanceUntilIdle()

            // Then
            coVerify {
                mockCityDao.searchCitiesByPrefix(prefix)
            }

            assert(response == cityModels)
        }

    @Test
    fun `searchCityByPrefix should returns empty list when prefix does not matches any of them`() =
        runTest(testDispatcher) {
            // Given
            val prefix = "Flo"
            val cities = emptyList<City>()
            val cityModels = emptyList<CityModel>()

            coEvery { mockCityDao.searchCitiesByPrefix(prefix) } returns cities

            // When
            val response = repositoryImpl.searchCityByPrefix(prefix)
            advanceUntilIdle()

            // Then
            coVerify {
                mockCityDao.searchCitiesByPrefix(prefix)
            }

            assert(response == cityModels)
        }
}
