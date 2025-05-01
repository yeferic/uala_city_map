package com.yeferic.ualacity.presentation.map.viewmodels

import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.domain.models.CityQueryResultModel
import com.yeferic.ualacity.domain.models.CoordinatesModel
import com.yeferic.ualacity.domain.usecases.RemoveCityAsFavoriteUseCase
import com.yeferic.ualacity.domain.usecases.SearchCityByQueryUseCase
import com.yeferic.ualacity.domain.usecases.SetCityAsFavoriteUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest {
    private lateinit var viewModel: MapViewModel

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var searchCityByQueryUseCaseMock: SearchCityByQueryUseCase

    @MockK
    private lateinit var setCityAsFavoriteUseCaseMock: SetCityAsFavoriteUseCase

    @MockK
    private lateinit var removeCityAsFavoriteUseCaseMock: RemoveCityAsFavoriteUseCase

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel =
            MapViewModel(
                ioDispatcher = testDispatcher,
                searchCityByQueryUseCase = searchCityByQueryUseCaseMock,
                setCityAsFavoriteUseCase = setCityAsFavoriteUseCaseMock,
                removeCityAsFavoriteUseCase = removeCityAsFavoriteUseCaseMock,
            )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(
            searchCityByQueryUseCaseMock,
            setCityAsFavoriteUseCaseMock,
            removeCityAsFavoriteUseCaseMock,
        )
    }

    @Test
    fun `onTextChanged should set new text value`() {
        // Given
        val newText = "New Text"

        // When
        viewModel.onTextChanged(newText)

        // Then
        assert(viewModel.query.value == newText)
        assert(viewModel.citySelected.value == null)
    }

    @Test
    fun `onCitySelected should set new city value`() {
        // Given
        val newCity =
            mockk<CityQueryResultModel> {
                every { text } returns "New City"
            }

        // When
        viewModel.onCitySelected(newCity)

        // Then
        verify { newCity.text }
        confirmVerified(newCity)

        assert(viewModel.query.value == newCity.text)
        assert(viewModel.citySelected.value == newCity)
    }

    @Test
    fun `changeFavoriteStatus should call to set favorite use case when city is not favorite`() =
        runTest {
            // Given
            val cityId = 1
            val newCity =
                CityQueryResultModel(
                    id = cityId,
                    text = "New City",
                    isFavorite = false,
                    coordinates = CoordinatesModel(0.0, 0.0),
                )

            val cityStates = mutableListOf<CityQueryResultModel?>()
            val collectJob =
                launch {
                    viewModel.citySelected.toList(cityStates)
                }

            viewModel.onCitySelected(newCity)

            coEvery { setCityAsFavoriteUseCaseMock(cityId.toString()) } returns
                flowOf(UseCaseStatus.Success(true))

            // When
            viewModel.changeFavoriteStatus()
            advanceUntilIdle()

            // Then
            verify {
                setCityAsFavoriteUseCaseMock.invoke(cityId.toString())
            }

            assert(cityStates.any { it?.isFavorite == true && it.id == cityId })

            collectJob.cancel()
        }

    @Test
    fun `changeFavoriteStatus should call to remove favorite use case when city is favorite`() =
        runTest {
            // Given
            val cityId = 1
            val newCity =
                CityQueryResultModel(
                    id = cityId,
                    text = "New City",
                    isFavorite = true,
                    coordinates = CoordinatesModel(0.0, 0.0),
                )

            val cityStates = mutableListOf<CityQueryResultModel?>()
            val collectJob =
                launch {
                    viewModel.citySelected.toList(cityStates)
                }

            viewModel.onCitySelected(newCity)

            coEvery { removeCityAsFavoriteUseCaseMock(cityId.toString()) } returns
                flowOf(UseCaseStatus.Success(true))

            // When
            viewModel.changeFavoriteStatus()
            advanceUntilIdle()

            // Then
            verify {
                removeCityAsFavoriteUseCaseMock.invoke(cityId.toString())
            }

            assert(cityStates.any { it?.isFavorite == false && it.id == cityId })

            collectJob.cancel()
        }

    @Test
    fun `queryResult should emit correct results when query is not blank`() =
        runTest {
            // Given
            val query = "Mad"
            val mockResult =
                listOf(
                    CityQueryResultModel(1, "Madrid [ES]", false, CoordinatesModel(0.0, 0.0)),
                )
            coEvery { searchCityByQueryUseCaseMock(query) } returns
                flowOf(UseCaseStatus.Success(mockResult))

            val cityStates = mutableListOf<List<CityQueryResultModel>>()
            val collectJob =
                launch {
                    viewModel.queryResult.toList(cityStates)
                }

            viewModel.onTextChanged(query)
            advanceTimeBy(500)

            // When

            val result = viewModel.queryResult.value
            advanceUntilIdle()

            // Then
            coVerify {
                searchCityByQueryUseCaseMock.invoke(query)
            }
            assert(result.isNotEmpty())
            collectJob.cancel()
        }
}
