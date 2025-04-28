package com.yeferic.ualacity.data.repositories

import com.yeferic.ualacity.data.sources.local.dao.CityDao
import com.yeferic.ualacity.data.sources.local.entities.mapToDomain
import com.yeferic.ualacity.data.sources.remote.CityApi
import com.yeferic.ualacity.data.sources.remote.dto.mapToDao
import com.yeferic.ualacity.di.IoDispatcher
import com.yeferic.ualacity.domain.models.CityModel
import com.yeferic.ualacity.domain.repositories.CityRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CityRepositoryImpl
    @Inject
    constructor(
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
        private val cityApi: CityApi,
        private val cityDao: CityDao,
    ) : CityRepository {
        override suspend fun fetchRemoteCities() {
            withContext(ioDispatcher) {
                cityDao.insertCities(cityApi.getCities().map { it.mapToDao() })
            }
        }

        override suspend fun isLocalCitiesDataLoaded(): Boolean =
            withContext(ioDispatcher) {
                cityDao.getCitiesCount() > 0
            }

        override suspend fun searchCityByPrefix(prefix: String): List<CityModel> =
            withContext(ioDispatcher) {
                cityDao.searchCitiesByPrefix(prefix = prefix).map { it.mapToDomain() }
            }
    }
