package com.yeferic.ualacity.presentation.map.uistates

import com.yeferic.ualacity.domain.models.CityQueryResultModel

data class MapScreenUiState(
    val isLoading: Boolean = false,
    val selectedCity: CityQueryResultModel? = null,
)
