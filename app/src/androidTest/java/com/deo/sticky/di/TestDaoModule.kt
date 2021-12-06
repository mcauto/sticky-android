package com.deo.sticky.di

import com.deo.sticky.features.checkin.category.models.CategoryDao
import com.deo.sticky.features.checkin.place.models.PlaceDao
import com.deo.sticky.features.checkout.models.AccumulateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DaoModule::class]
)
object TestDaoModule {
    @Provides
    @Singleton
    fun providePlaceDao(
        database: StickyDatabase
    ): PlaceDao = database.getPlaceDao()

    @Provides
    @Singleton
    fun provideCategoryDao(
        database: StickyDatabase
    ): CategoryDao = database.getCategoryDao()

    @Provides
    @Singleton
    fun provideAccumulateDao(
        database: StickyDatabase
    ): AccumulateDao = database.getAccumulateDao()
}
