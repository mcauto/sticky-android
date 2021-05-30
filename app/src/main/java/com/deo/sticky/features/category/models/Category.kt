package com.deo.sticky.features.category.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.deo.sticky.R

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String? = null,
    var image: String? = null,
    var count: Long? = null,
    @Ignore var isChecked: Boolean = false,
) {
    constructor() : this(null, null, null, null, false)

    val imageResourceId: Int
        get() = when (image) {
            "ic_office" -> R.drawable.ic_office
            "ic_cafe" -> R.drawable.ic_cafe
            "ic_house" -> R.drawable.ic_house
            "ic_game" -> R.drawable.ic_game
            "ic_restaurant" -> R.drawable.ic_restaurant
            "ic_shop" -> R.drawable.ic_shop
            "ic_heart" -> R.drawable.ic_heart
            "ic_drink" -> R.drawable.ic_drink
            else -> R.drawable.ic_office
        }
    val backgroundResourceId: Int
        get() = when (image) {
            "ic_office" -> R.drawable.circle_blue
            "ic_cafe" -> R.drawable.circle_green
            "ic_house" -> R.drawable.circle_yellow
            "ic_game" -> R.drawable.circle_orange
            "ic_restaurant" -> R.drawable.circle_blue
            "ic_shop" -> R.drawable.circle_purple
            "ic_heart" -> R.drawable.circle_red
            "ic_drink" -> R.drawable.circle_green
            else -> R.drawable.circle_blue
        }
}
