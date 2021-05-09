package com.deo.sticky.di

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import co.anbora.labs.spatia.builder.SpatiaRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
@Module
class TestDatabaseModule {
    /**
     * 장소 데이터베이스
     */
    @Singleton
    @Provides
    fun provideSpatiaDatabase(
        @ApplicationContext
        context: Context
    ) = SpatiaRoom.databaseBuilder(
        context.applicationContext,
        StickyDatabase::class.java,
        "sticky-test.db"
    ).addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
        }
    }).fallbackToDestructiveMigration().build()
//        .setTransactionExecutor(
//        Executors.newSingleThreadExecutor()
//    ).build()
}
