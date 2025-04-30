package com.yeferic.ualacity.domain.repositories

interface LocalRepository {
    fun saveFavoriteCity(cityId: String)

    fun getFavoriteCities(): Set<String>

    fun removeFavoriteCity(cityId: String)
}
