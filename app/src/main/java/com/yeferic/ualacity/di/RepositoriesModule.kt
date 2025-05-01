package com.yeferic.ualacity.di

import com.yeferic.ualacity.data.repositories.CityRepositoryImpl
import com.yeferic.ualacity.data.repositories.LocalRepositoryImpl
import com.yeferic.ualacity.domain.repositories.CityRepository
import com.yeferic.ualacity.domain.repositories.LocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindCityRepository(impl: CityRepositoryImpl): CityRepository

    @Binds
    abstract fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository
}
