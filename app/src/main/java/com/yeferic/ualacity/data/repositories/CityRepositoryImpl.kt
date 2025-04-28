package com.yeferic.ualacity.data.repositories

import com.google.gson.Gson
import com.yeferic.ualacity.data.sources.remote.CityApi
import com.yeferic.ualacity.domain.repositories.CityRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityRepositoryImpl
    @Inject
    constructor(
        private val cityApi: CityApi,
        private val gson: Gson,
    ) : CityRepository {
        override suspend fun fetchRemoteCities() {
            withContext(Dispatchers.IO) {
                val cities = cityApi.getCities()
                println(cities)
            }
        }
    }
