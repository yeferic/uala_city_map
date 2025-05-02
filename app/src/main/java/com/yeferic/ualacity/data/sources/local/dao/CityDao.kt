package com.yeferic.ualacity.data.sources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeferic.ualacity.data.sources.local.entities.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<City>)

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun getCitiesCount(): Int

    @Query(
        "SELECT * FROM cities WHERE name LIKE :prefix " +
            "|| '%' COLLATE NOCASE ORDER BY name ASC, country ASC",
    )
    suspend fun searchCitiesByPrefix(prefix: String): List<City>
}
