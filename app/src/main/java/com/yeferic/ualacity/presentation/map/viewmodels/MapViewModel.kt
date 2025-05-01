package com.yeferic.ualacity.presentation.map.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.di.IoDispatcher
import com.yeferic.ualacity.domain.models.CityQueryResultModel
import com.yeferic.ualacity.domain.usecases.RemoveCityAsFavoriteUseCase
import com.yeferic.ualacity.domain.usecases.SearchCityByQueryUseCase
import com.yeferic.ualacity.domain.usecases.SetCityAsFavoriteUseCase
import com.yeferic.ualacity.presentation.map.uistates.MapScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class MapViewModel
    @Inject
    constructor(
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
        private val searchCityByQueryUseCase: SearchCityByQueryUseCase,
        private val setCityAsFavoriteUseCase: SetCityAsFavoriteUseCase,
        private val removeCityAsFavoriteUseCase: RemoveCityAsFavoriteUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MapScreenUiState())
        val uiState: StateFlow<MapScreenUiState> = _uiState

        private val _query = MutableStateFlow(String())
        val query: StateFlow<String> = _query.asStateFlow()

        private val _citySelected = MutableStateFlow<CityQueryResultModel?>(null)
        val citySelected: StateFlow<CityQueryResultModel?> = _citySelected.asStateFlow()

        val queryResult: StateFlow<List<CityQueryResultModel>> =
            _query
                .debounce(300)
                .map { it.trim() }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        flowOf(emptyList())
                    } else {
                        searchCityByQueryUseCase(query)
                            .map { result ->
                                when (result) {
                                    is UseCaseStatus.Success -> result.data
                                    is UseCaseStatus.Error -> emptyList()
                                }
                            }
                    }
                }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        fun onTextChanged(newText: String) {
            _query.value = newText
            _citySelected.value = null
        }

        fun onCitySelected(city: CityQueryResultModel?) {
            _citySelected.value = city
            _query.value = city?.text ?: String()
        }

        fun changeFavoriteStatus() {
            if (_citySelected.value?.isFavorite == true) {
                removeCityAsFavorite()
            } else {
                setCityAsFavorite()
            }
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        fun setCityAsFavorite() {
            _citySelected.value?.let {
                viewModelScope.launch(ioDispatcher) {
                    setCityAsFavoriteUseCase(it.id.toString()).collect { result ->
                        when (result) {
                            is UseCaseStatus.Success -> {
                                _citySelected.update { it!!.copy(isFavorite = true) }
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }

        @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
        fun removeCityAsFavorite() {
            _citySelected.value?.let {
                viewModelScope.launch(ioDispatcher) {
                    removeCityAsFavoriteUseCase(it.id.toString()).collect { result ->
                        when (result) {
                            is UseCaseStatus.Success -> {
                                _citySelected.update { it!!.copy(isFavorite = false) }
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    }
