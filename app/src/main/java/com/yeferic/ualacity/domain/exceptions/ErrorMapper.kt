package com.yeferic.ualacity.domain.exceptions

interface ErrorMapper {
    fun getError(throwable: Throwable): ErrorEntity
}

interface CityRepositoryErrorMapper : ErrorMapper
