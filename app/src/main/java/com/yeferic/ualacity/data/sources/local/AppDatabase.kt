package com.yeferic.ualacity.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yeferic.ualacity.data.sources.local.dao.City

@Database(
    entities = [City::class],
    version = AppDatabase.VERSION,
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }
}
