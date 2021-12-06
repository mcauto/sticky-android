package com.deo.sticky.features.checkout.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.deo.sticky.base.DeleteAndUpsertDao

@Dao
interface AccumulateDao : DeleteAndUpsertDao<Accumulate> {
    @Transaction
    @Query("SELECT * FROM accumulates")
    suspend fun findAll(): List<AccumulateReceipt>

    @Transaction
    @Query("SELECT * FROM accumulates WHERE id = :id")
    suspend fun get(id: Long): AccumulateReceipt?

    @Transaction
    @Insert(entity = Accumulate::class)
    suspend fun start(accumulate: AccumulateBase): Long

    @Transaction
    suspend fun end(id: Long) {
        updateEnd(id)
        updateValue(id)
    }

    @Query(
        """
        UPDATE accumulates 
        SET `end` = CURRENT_TIMESTAMP
        WHERE id = :id;
    """
    )
    suspend fun updateEnd(id: Long)

    @Query(
        """
            UPDATE accumulates
            SET `value` = `end` - `start`
            WHERE id = :id;
        """
    )
    suspend fun updateValue(id: Long)
}
