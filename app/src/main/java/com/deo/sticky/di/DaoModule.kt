package com.deo.sticky.di

import com.deo.sticky.features.checkin.category.models.CategoryDao
import com.deo.sticky.features.checkin.place.models.PlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DaoModule {
    @Provides
    @ViewModelScoped
    fun providePlaceDao(
        database: StickyDatabase
    ): PlaceDao = database.getPlaceDao()

    @Provides
    @ViewModelScoped
    fun provideCategoryDao(
        database: StickyDatabase
    ): CategoryDao = database.getCategoryDao()
}
