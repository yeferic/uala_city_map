package com.yeferic.ualacity.data.repositories

import android.content.SharedPreferences
import androidx.core.content.edit
import com.yeferic.ualacity.domain.repositories.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl
    @Inject
    constructor(
        private val sharedPreferences: SharedPreferences,
    ) : LocalRepository {
        companion object {
            private const val KEY_FAVORITE_CITIES = "favorite_cities"
        }

        override fun saveFavoriteCity(cityId: String) {
            val currentSet = getFavoriteCities().toMutableSet()
            currentSet.add(cityId)
            sharedPreferences.edit { putStringSet(KEY_FAVORITE_CITIES, currentSet) }
        }

        override fun getFavoriteCities(): Set<String> =
            sharedPreferences.getStringSet(KEY_FAVORITE_CITIES, emptySet()) ?: emptySet()

        override fun removeFavoriteCity(cityId: String) {
            val currentSet = getFavoriteCities().toMutableSet()
            currentSet.remove(cityId)
            sharedPreferences.edit { putStringSet(KEY_FAVORITE_CITIES, currentSet) }
        }
    }
