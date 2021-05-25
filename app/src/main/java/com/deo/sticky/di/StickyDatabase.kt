package com.deo.sticky.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deo.sticky.features.category.models.Category
import com.deo.sticky.features.category.models.CategoryDao
import com.deo.sticky.features.place.models.PlaceDao
import com.deo.sticky.features.place.models.entity.Place

@Database(entities = [Place::class, Category::class], version = 1)
abstract class StickyDatabase : RoomDatabase() {
    abstract fun getPlaceDao(): PlaceDao
    abstract fun getCategoryDao(): CategoryDao
}
