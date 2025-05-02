package com.yeferic.ualacity.di

import android.app.Application
import androidx.room.Room
import com.yeferic.ualacity.BuildConfig
import com.yeferic.ualacity.data.sources.local.AppDatabase
import com.yeferic.ualacity.data.sources.local.dao.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    @DbName
    fun provideDBName(): String = BuildConfig.LOCAL_DB_NAME

    @Provides
    @Singleton
    fun provideRoomDatabase(
        application: Application,
        @DbName dbName: String,
    ): AppDatabase =
        Room
            .databaseBuilder(application, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideCityDao(appDataBase: AppDatabase): CityDao = appDataBase.cityDao()
}
