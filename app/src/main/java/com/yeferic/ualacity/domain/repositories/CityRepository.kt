package com.yeferic.ualacity.domain.repositories

interface CityRepository {
    suspend fun fetchRemoteCities()
}
