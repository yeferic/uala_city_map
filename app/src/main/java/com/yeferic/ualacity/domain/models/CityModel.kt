package com.yeferic.ualacity.domain.models

data class CityModel(
    val id: Int,
    val country: String,
    val name: String,
    val coordinates: CoordinatesModel,
)
