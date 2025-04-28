package com.yeferic.ualacity.data.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.yeferic.ualacity.data.sources.local.entities.Coord

data class CoordinatesDTO(
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("lat")
    val lat: Double,
)

fun CoordinatesDTO.mapToDao(): Coord =
    Coord(
        lat = this.lat,
        lon = this.lon,
    )
