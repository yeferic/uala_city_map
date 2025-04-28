package com.yeferic.ualacity.di

import com.yeferic.ualacity.data.errormappers.CityRepositoryErrorMapperImpl
import com.yeferic.ualacity.domain.exceptions.CityRepositoryErrorMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorMappersModule {
    @Binds
    abstract fun bindCityRepositoryErrorMapper(
        impl: CityRepositoryErrorMapperImpl,
    ): CityRepositoryErrorMapper
}
