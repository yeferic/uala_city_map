package com.yeferic.ualacity.data.sources.local.dao

import com.yeferic.ualacity.domain.models.CoordinatesModel

data class Coord(
    val lon: Double,
    val lat: Double,
)

fun Coord.mapToDomain(): CoordinatesModel =
    CoordinatesModel(
        latitude = this.lat,
        longitude = this.lon,
    )
