package com.yeferic.ualacity.presentation.load.uistates

import com.yeferic.ualacity.domain.exceptions.ErrorEntity

data class LoadScreenUiState(
    val isLoading: Boolean = false,
    val error: ErrorEntity? = null,
    val fetchRemoteData: Boolean = false,
    val dataLoadedSuccessfully: Boolean = false,
)
