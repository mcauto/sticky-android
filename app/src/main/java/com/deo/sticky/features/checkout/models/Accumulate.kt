package com.deo.sticky.features.checkout.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.deo.sticky.features.checkin.category.models.Category
import com.deo.sticky.features.checkin.place.models.entity.Place

@Entity(
    tableName = "accumulates",
    indices = [Index("placeId"), Index("categoryId")],
    foreignKeys = [
        ForeignKey(
            entity = Place::class,
            parentColumns = ["id"],
            childColumns = ["placeId"]
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        )
    ]
)
data class Accumulate(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    val placeId: Long,
    val categoryId: Long,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val start: Long,
    val end: Long? = null,
    val value: Long? = 0,
    val memoThumbnail: String? = null,
    val memoTitle: String? = null,
    val memoContents: String? = null,
)

data class AccumulateBase(
    val placeId: Long,
    val categoryId: Long,
)

/**
 * 누적 시간 영수증
 */
data class AccumulateReceipt(
    @Embedded
    var accumulate: Accumulate,
    @Relation(
        parentColumn = "placeId",
        entityColumn = "id"
    )
    val place: Place,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category,
)
