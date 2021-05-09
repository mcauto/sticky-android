package com.deo.sticky.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deo.sticky.features.map.models.PlaceDao
import com.deo.sticky.features.map.models.entity.Place

@Database(entities = [Place::class], version = 1)
abstract class StickyDatabase : RoomDatabase() {
    abstract fun getPlaceDao(): PlaceDao
}
