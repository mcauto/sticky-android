package com.deo.sticky.features.map.models

import androidx.room.Dao
import androidx.room.Query
import androidx.room.SkipQueryVerification
import com.deo.sticky.base.BaseDao
import com.deo.sticky.features.map.models.entity.Place
import com.deo.sticky.features.map.models.entity.PlaceWithDistance

@Dao
interface PlaceDao : BaseDao<Place> {
    /**
     * 장소 추가하기 (+ 좌표 저장)
     */
    @Query(
        """
        INSERT INTO places (name, latitude, longitude, geometry) 
             VALUES (:name, :latitude, :longitude, MakePoint(:latitude, :longitude, 4326))
        """
    )
    @SkipQueryVerification
    suspend fun add(
        name: String,
        latitude: Double,
        longitude: Double,
    ): Long

    /**
     * 반경 내 장소 리스트 불러오기
     */
    @Query(
        """
       SELECT id,
              name,
              latitude,
              longitude,
              geometry,
              GeodesicLength(ShortestLine(geometry, MakePoint(:latitude, :longitude, 4326))) as distance
        FROM places
       WHERE PtDistWithin(geometry, MakePoint(:latitude, :longitude, 4326), :radius)
       ORDER BY distance ASC
    """
    )
    @SkipQueryVerification
    suspend fun getPlacesWithinRadius(
        latitude: Double,
        longitude: Double,
        radius: Int
    ): List<PlaceWithDistance>
}
