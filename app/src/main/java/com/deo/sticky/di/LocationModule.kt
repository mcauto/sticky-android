package com.deo.sticky.di

import android.content.Context
import com.deo.sticky.BuildConfig
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {
    @Singleton
    @Provides
    fun provideLocationClient(
        @ApplicationContext
        context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Singleton
    @Provides
    fun provideLocationRequest(): LocationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 30 * 1000
    }

    @Singleton
    @Provides
    fun providePlacesClient(
        @ApplicationContext
        context: Context
    ): PlacesClient {
        Places.initialize(context, BuildConfig.MAPS_API_KEY)
        return Places.createClient(context)
    }
}
