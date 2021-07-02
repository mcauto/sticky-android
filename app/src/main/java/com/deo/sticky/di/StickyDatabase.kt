package com.deo.sticky.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deo.sticky.features.checkin.category.models.Category
import com.deo.sticky.features.checkin.category.models.CategoryDao
import com.deo.sticky.features.checkin.place.models.PlaceDao
import com.deo.sticky.features.checkin.place.models.entity.Place

@Database(entities = [Place::class, Category::class], version = 2)
abstract class StickyDatabase : RoomDatabase() {
    abstract fun getPlaceDao(): PlaceDao
    abstract fun getCategoryDao(): CategoryDao
}
