package com.yeferic.ualacity.data.repositories

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.perf.FirebasePerformance
import com.yeferic.ualacity.data.sources.local.dao.CityDao
import com.yeferic.ualacity.data.sources.local.entities.mapToDomain
import com.yeferic.ualacity.data.sources.remote.CityApi
import com.yeferic.ualacity.data.sources.remote.dto.mapToDao
import com.yeferic.ualacity.di.IoDispatcher
import com.yeferic.ualacity.domain.models.CityModel
import com.yeferic.ualacity.domain.repositories.CityRepository
import com.yeferic.ualacity.domain.repositories.LocalRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CityRepositoryImpl
    @Inject
    constructor(
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
        private val cityApi: CityApi,
        private val cityDao: CityDao,
        private val localRepository: LocalRepository,
        private val firebasePerformance: FirebasePerformance,
        private val firebaseCrashlytics: FirebaseCrashlytics,
    ) : CityRepository {
        companion object {
            private const val FETCH_CITIES_METHOD = "fetchRemoteCities"
        }

        override suspend fun fetchRemoteCities() {
            val trace = firebasePerformance.newTrace(FETCH_CITIES_METHOD)
            trace.start()
            try {
                withContext(ioDispatcher) {
                    cityDao.insertCities(cityApi.getCities().map { it.mapToDao() })
                }
            } catch (e: Exception) {
                firebaseCrashlytics.recordException(e)
                throw e
            } finally {
                trace.stop()
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

        override suspend fun getFavoriteCities(): List<String> =
            localRepository.getFavoriteCities().toList()

        override suspend fun saveFavoriteCity(city: String) = localRepository.saveFavoriteCity(city)

        override suspend fun removeFavoriteCity(city: String) =
            localRepository.removeFavoriteCity(city)
    }
