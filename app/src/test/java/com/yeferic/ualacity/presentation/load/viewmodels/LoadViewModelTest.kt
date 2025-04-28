package com.yeferic.ualacity.presentation.load.viewmodels

import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.data.errormappers.CityRepositoryError
import com.yeferic.ualacity.domain.usecases.FetchRemoteDataUseCase
import com.yeferic.ualacity.domain.usecases.GetLocalDataIsLoadedUseCase
import com.yeferic.ualacity.presentation.load.uistates.LoadScreenUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadViewModelTest {
    private lateinit var viewModel: LoadViewModel

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var fetchRemoteDataUseCaseMock: FetchRemoteDataUseCase

    @MockK
    private lateinit var getLocalDataIsLoadedUseCaseMock: GetLocalDataIsLoadedUseCase

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel =
            LoadViewModel(
                ioDispatcher = testDispatcher,
                getLocalDataUseCase = getLocalDataIsLoadedUseCaseMock,
                fetchRemoteDataUseCase = fetchRemoteDataUseCaseMock,
            )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(
            fetchRemoteDataUseCaseMock,
            getLocalDataIsLoadedUseCaseMock,
        )
    }

    @Test
    fun `getIsDataLoaded should sets isLoading true and data loaded successfully when use case returns true`() =
        runTest(testDispatcher) {
            // Given
            val dataLoaded = true
            val uiStates = mutableListOf<LoadScreenUiState>()
            val collectJob =
                launch {
                    viewModel.uiState.toList(uiStates)
                }
            coEvery { getLocalDataIsLoadedUseCaseMock.invoke() } returns
                flowOf(UseCaseStatus.Success(dataLoaded))

            // When
            viewModel.getIsDataLoaded()
            advanceUntilIdle()

            // Then
            coVerify {
                getLocalDataIsLoadedUseCaseMock.invoke()
            }

            assert(uiStates.any { it.isLoading })
            assert(uiStates.any { !it.isLoading && it.dataLoadedSuccessfully == dataLoaded })

            collectJob.cancel()
        }

    @Test
    fun `getIsDataLoaded should sets isLoading true and error entity when use case returns exception`() =
        runTest(testDispatcher) {
            // Given
            val error = CityRepositoryError.DataNotLoadedError
            val uiStates = mutableListOf<LoadScreenUiState>()
            val collectJob =
                launch {
                    viewModel.uiState.toList(uiStates)
                }
            coEvery { getLocalDataIsLoadedUseCaseMock.invoke() } returns
                flowOf(UseCaseStatus.Error(error))

            // When
            viewModel.getIsDataLoaded()
            advanceUntilIdle()

            // Then
            coVerify {
                getLocalDataIsLoadedUseCaseMock.invoke()
            }

            assert(uiStates.any { it.isLoading })
            assert(uiStates.any { !it.isLoading && it.error == error })

            collectJob.cancel()
        }

    @Test
    fun `getIsDataLoaded should sets isLoading true and call to fetch data returns false`() =
        runTest(testDispatcher) {
            // Given
            val dataLoaded = false
            val uiStates = mutableListOf<LoadScreenUiState>()
            val collectJob =
                launch {
                    viewModel.uiState.toList(uiStates)
                }
            coEvery { getLocalDataIsLoadedUseCaseMock.invoke() } returns
                flowOf(UseCaseStatus.Success(dataLoaded))

            coEvery { fetchRemoteDataUseCaseMock.invoke() } returns flowOf()

            // When
            viewModel.getIsDataLoaded()
            advanceUntilIdle()

            // Then
            coVerify {
                getLocalDataIsLoadedUseCaseMock.invoke()
                fetchRemoteDataUseCaseMock.invoke()
            }

            assert(uiStates.any { it.isLoading })
            assert(
                uiStates.any { it.fetchRemoteData },
            )

            collectJob.cancel()
        }

    @Test
    fun `fetchRemoteData should sets data is loaded successfully when use case returns true`() =
        runTest(testDispatcher) {
            // Given
            val dataLoaded = false
            val uiStates = mutableListOf<LoadScreenUiState>()
            val collectJob =
                launch {
                    viewModel.uiState.toList(uiStates)
                }
            coEvery { fetchRemoteDataUseCaseMock.invoke() } returns
                flowOf(UseCaseStatus.Success(dataLoaded))

            // When
            viewModel.fetchRemoteData()
            advanceUntilIdle()

            // Then
            coVerify {
                fetchRemoteDataUseCaseMock.invoke()
            }

            assert(
                uiStates.any { it.dataLoadedSuccessfully },
            )

            collectJob.cancel()
        }

    @Test
    fun `fetchRemoteData should sets error entity when use case returns exception`() =
        runTest(testDispatcher) {
            // Given
            val error = CityRepositoryError.DataNotLoadedError
            val uiStates = mutableListOf<LoadScreenUiState>()
            val collectJob =
                launch {
                    viewModel.uiState.toList(uiStates)
                }
            coEvery { fetchRemoteDataUseCaseMock.invoke() } returns
                flowOf(UseCaseStatus.Error(error))

            // When
            viewModel.fetchRemoteData()
            advanceUntilIdle()

            // Then
            coVerify {
                fetchRemoteDataUseCaseMock.invoke()
            }

            assert(uiStates.any { it.error == error })

            collectJob.cancel()
        }
}
