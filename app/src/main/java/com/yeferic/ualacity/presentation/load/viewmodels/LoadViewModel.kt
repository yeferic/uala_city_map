package com.yeferic.ualacity.presentation.load.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.di.IoDispatcher
import com.yeferic.ualacity.domain.usecases.FetchRemoteDataUseCase
import com.yeferic.ualacity.domain.usecases.GetLocalDataIsLoadedUseCase
import com.yeferic.ualacity.domain.usecases.TrackEventUseCase
import com.yeferic.ualacity.presentation.load.uistates.LoadScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoadViewModel
    @Inject
    constructor(
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
        private val getLocalDataUseCase: GetLocalDataIsLoadedUseCase,
        private val fetchRemoteDataUseCase: FetchRemoteDataUseCase,
        private val trackEventUseCase: TrackEventUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(LoadScreenUiState(isLoading = true))
        val uiState: StateFlow<LoadScreenUiState> = _uiState

        fun getIsDataLoaded() {
            viewModelScope.launch(ioDispatcher) {
                _uiState.value = LoadScreenUiState(isLoading = true)
                getLocalDataUseCase().collect { status ->
                    when (status) {
                        is UseCaseStatus.Success -> {
                            if (status.data) {
                                _uiState.value =
                                    LoadScreenUiState(dataLoadedSuccessfully = true)
                            } else {
                                _uiState.value =
                                    LoadScreenUiState(isLoading = true, fetchRemoteData = true)
                                fetchRemoteData()
                            }
                        }

                        is UseCaseStatus.Error -> {
                            _uiState.update {
                                it.copy(isLoading = false, error = status.errorEntity)
                            }
                        }
                    }
                }
            }
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        fun fetchRemoteData() {
            viewModelScope.launch(ioDispatcher) {
                fetchRemoteDataUseCase()
                    .onStart { trackEventUseCase.trackStartLoadDataEvent() }
                    .collect { status ->
                        when (status) {
                            is UseCaseStatus.Success -> {
                                trackEventUseCase.trackFinishLoadDataEvent()
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        dataLoadedSuccessfully = true,
                                        error = null,
                                    )
                                }
                            }
                            is UseCaseStatus.Error -> {
                                trackEventUseCase.trackFailLoadDataEvent()
                                _uiState.update {
                                    it.copy(isLoading = false, error = status.errorEntity)
                                }
                            }
                        }
                    }
            }
        }

        fun trackRetryLoadDataEvent() {
            trackEventUseCase.trackRetryLoadDataEvent()
        }
    }
