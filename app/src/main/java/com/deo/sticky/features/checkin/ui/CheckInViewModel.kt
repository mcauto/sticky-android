package com.deo.sticky.features.checkin.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.deo.sticky.features.category.models.Category
import com.deo.sticky.features.category.models.CategoryDao
import com.deo.sticky.features.place.models.PlaceDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val placeDao: PlaceDao,
    categoryDao: CategoryDao,
) : ViewModel(), LifecycleObserver {
    val categories = categoryDao.getAll().asLiveData()
    val placeName = ObservableField("")

    var selectedCategory = ObservableField<Category>()
    fun onCategorySelected(category: Category) = viewModelScope.launch {
        selectedCategory.set(category)
    }
}
