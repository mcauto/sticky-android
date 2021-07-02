package com.deo.sticky.features.checkin.category.models

import androidx.room.Dao
import androidx.room.Query
import com.deo.sticky.base.DeleteAndUpsertDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : DeleteAndUpsertDao<Category> {
    @Query(
        """
        SELECT *
        FROM categories
    """
    )
    fun getAll(): Flow<List<Category>>
}
