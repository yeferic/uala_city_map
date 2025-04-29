package com.yeferic.ualacity.domain.usecases

import com.yeferic.ualacity.core.commons.UseCaseStatus
import com.yeferic.ualacity.domain.exceptions.CityRepositoryErrorMapper
import com.yeferic.ualacity.domain.repositories.CityRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class GetLocalDataIsLoadedUseCase
    @Inject
    constructor(
        private val cityRepository: CityRepository,
        private val cityRepositoryErrorMapper: CityRepositoryErrorMapper,
    ) {
        operator fun invoke(): Flow<UseCaseStatus<Boolean>> =
            flow {
                runCatching {
                    emit(UseCaseStatus.Success(cityRepository.isLocalCitiesDataLoaded()))
                }.onFailure {
                    emit(UseCaseStatus.Error(cityRepositoryErrorMapper.getError(it)))
                }
            }
    }
