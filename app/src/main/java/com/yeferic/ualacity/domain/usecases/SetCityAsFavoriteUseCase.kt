package com.yeferic.ualacity.domain.usecases

import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.domain.exceptions.CityRepositoryErrorMapper
import com.yeferic.ualacity.domain.repositories.CityRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SetCityAsFavoriteUseCase
    @Inject
    constructor(
        private val cityRepository: CityRepository,
        private val cityRepositoryErrorMapper: CityRepositoryErrorMapper,
    ) {
        operator fun invoke(cityId: String): Flow<UseCaseStatus<Boolean>> =
            flow {
                runCatching {
                    cityRepository.saveFavoriteCity(cityId)
                    emit(UseCaseStatus.Success(true))
                }.onFailure {
                    emit(UseCaseStatus.Error(cityRepositoryErrorMapper.getError(it)))
                }
            }
    }
