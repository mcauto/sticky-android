package com.deo.sticky.di

import com.deo.sticky.features.category.models.CategoryDao
import com.deo.sticky.features.place.models.PlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun providePlaceDao(
        database: StickyDatabase
    ): PlaceDao = database.getPlaceDao()

    @Singleton
    @Provides
    fun provideCategoryDao(
        database: StickyDatabase
    ): CategoryDao = database.getCategoryDao()
}
