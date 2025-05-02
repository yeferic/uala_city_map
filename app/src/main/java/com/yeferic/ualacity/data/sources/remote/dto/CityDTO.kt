package com.yeferic.ualacity.data.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.yeferic.ualacity.data.sources.local.entities.City

data class CityDTO(
    @SerializedName("_id")
    val id: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("coord")
    val coordinates: CoordinatesDTO,
)

fun CityDTO.mapToDao(): City =
    City(
        id = this.id,
        country = country,
        name = name,
        coord = coordinates.mapToDao(),
    )
