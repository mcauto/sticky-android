package com.deo.sticky.features.checkin.place.models.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 장소
 *
 * name: 장소명
 * latitude: 위도
 * longitude: 경도
 * geometry: spatialite의 geometry
 */
@Entity(tableName = "places")
data class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val geometry: ByteArray? = null
)

data class PlaceWithDistance(
    @Embedded
    val place: Place?,
    val distance: Double?
)
