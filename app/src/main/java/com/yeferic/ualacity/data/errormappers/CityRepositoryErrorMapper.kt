package com.yeferic.ualacity.data.errormappers

import com.yeferic.ualacity.domain.exceptions.CityRepositoryErrorMapper
import com.yeferic.ualacity.domain.exceptions.ErrorEntity
import javax.inject.Inject
import retrofit2.HttpException

class CityRepositoryErrorMapperImpl
    @Inject
    constructor() : CityRepositoryErrorMapper {
        override fun getError(throwable: Throwable): ErrorEntity =
            when (throwable) {
                is HttpException -> {
                    CityRepositoryError.ServiceError
                }

                else ->
                    CityRepositoryError.DataNotLoadedError
            }
    }

sealed class CityRepositoryError : ErrorEntity() {
    data object ServiceError : ErrorEntity()

    data object DataNotLoadedError : ErrorEntity()
}
