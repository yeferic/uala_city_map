package com.yeferic.ualacity.domain.models

data class CityQueryResultModel(
    val id: Int,
    val text: String,
    val isFavorite: Boolean,
    val coordinates: CoordinatesModel,
)
