package com.yeferic.ualacity.domain.usecases

import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.domain.exceptions.CityRepositoryErrorMapper
import com.yeferic.ualacity.domain.models.CityQueryResultModel
import com.yeferic.ualacity.domain.repositories.CityRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class SearchCityByQueryUseCase
    @Inject
    constructor(
        private val cityRepository: CityRepository,
        private val cityRepositoryErrorMapper: CityRepositoryErrorMapper,
    ) {
        operator fun invoke(query: String): Flow<UseCaseStatus<List<CityQueryResultModel>>> =
            flow {
                runCatching {
                    val favorites = cityRepository.getFavoriteCities()
                    val favoriteIds = favorites.map { it.toInt() }.toSet()

                    val result =
                        cityRepository.searchCityByPrefix(query).map { city ->
                            CityQueryResultModel(
                                id = city.id,
                                text = "${city.name} [${city.country}]",
                                isFavorite = city.id in favoriteIds,
                                coordinates = city.coordinates,
                            )
                        }
                    emit(UseCaseStatus.Success(result))
                }.onFailure {
                    emit(UseCaseStatus.Error(cityRepositoryErrorMapper.getError(it)))
                }
            }
    }
