package com.deo.sticky.di

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import co.anbora.labs.spatia.builder.SpatiaRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ) = SpatiaRoom.databaseBuilder(
        context,
        StickyDatabase::class.java,
        "sticky.db"
    ).addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // add init query
        }
    }).fallbackToDestructiveMigration().build()
//        .setTransactionExecutor(
//        Executors.newSingleThreadExecutor()
//    ).build()
}
