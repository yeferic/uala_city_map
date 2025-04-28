package com.yeferic.ualacity.data.sources.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yeferic.ualacity.domain.models.CityModel

@Entity(tableName = "cities")
data class City(
    @PrimaryKey val id: Int,
    val country: String,
    val name: String,
    @Embedded val coord: Coord,
)

fun City.mapToDomain(): CityModel =
    CityModel(
        id = this.id,
        country = this.country,
        name = this.name,
        coordinates = coord.mapToDomain(),
    )
