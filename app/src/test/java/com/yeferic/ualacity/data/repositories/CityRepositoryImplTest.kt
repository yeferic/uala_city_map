package com.yeferic.ualacity.data.repositories

import com.yeferic.ualacity.data.sources.local.dao.CityDao
import com.yeferic.ualacity.data.sources.remote.CityApi
import com.yeferic.ualacity.data.sources.remote.dto.CityDTO
import com.yeferic.ualacity.data.sources.remote.dto.CoordinatesDTO
import com.yeferic.ualacity.data.sources.remote.dto.mapToDao
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
                        name = "City A",
                        country = "Country A",
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
}
