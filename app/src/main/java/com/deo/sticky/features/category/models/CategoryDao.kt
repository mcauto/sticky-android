package com.deo.sticky.features.category.models

import androidx.room.Dao
import androidx.room.Query
import com.deo.sticky.base.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao : BaseDao<Category> {
    @Query(
        """
        SELECT *
        FROM categories
    """
    )
    fun getAll(): Flow<List<Category>>
}
