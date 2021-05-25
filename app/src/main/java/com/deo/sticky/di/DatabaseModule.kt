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

val defaultCategorySQL = """
INSERT OR REPLACE INTO `categories` (`id`, `name`, `image`, `count`)
VALUES (1, '회사', 'ic_office', 0),
    (2, '커피 ∙ 티', 'ic_cafe', 0),
    (3, '집', 'ic_house', 0),
    (4, '취미 ∙ 여가', 'ic_game', 0),
    (5, '밥', 'ic_restaurant', 0),
    (6, '쇼핑', 'ic_shop', 0),
    (7, '데이트', 'ic_heart', 0),
    (8, '술', 'ic_drink', 0);
""".trimIndent()

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
            db.execSQL(defaultCategorySQL)
            // add init query
        }
    }).fallbackToDestructiveMigration().build()
//        .setTransactionExecutor(
//        Executors.newSingleThreadExecutor()
//    ).build()
}
