package com.yeferic.ualacity.domain.repositories

import com.yeferic.ualacity.domain.models.CityModel

interface CityRepository {
    suspend fun fetchRemoteCities()

    suspend fun isLocalCitiesDataLoaded(): Boolean

    suspend fun searchCityByPrefix(prefix: String): List<CityModel>

    suspend fun getFavoriteCities(): List<String>

    suspend fun saveFavoriteCity(city: String)

    suspend fun removeFavoriteCity(city: String)
}
