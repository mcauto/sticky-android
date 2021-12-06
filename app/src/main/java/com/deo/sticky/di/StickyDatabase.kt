package com.deo.sticky.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deo.sticky.features.checkin.category.models.Category
import com.deo.sticky.features.checkin.category.models.CategoryDao
import com.deo.sticky.features.checkin.place.models.PlaceDao
import com.deo.sticky.features.checkin.place.models.entity.Place
import com.deo.sticky.features.checkout.models.Accumulate
import com.deo.sticky.features.checkout.models.AccumulateDao

@Database(entities = [Place::class, Category::class, Accumulate::class], version = 1)
abstract class StickyDatabase : RoomDatabase() {
    abstract fun getPlaceDao(): PlaceDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getAccumulateDao(): AccumulateDao
}
