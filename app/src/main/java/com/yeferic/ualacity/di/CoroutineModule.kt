package com.yeferic.ualacity.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {
    @Provides
    @IoDispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
