package com.deo.sticky.features.checkin

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.deo.sticky.features.checkin.category.models.Category
import com.deo.sticky.features.checkin.category.models.CategoryDao
import com.deo.sticky.features.checkin.place.models.PlaceDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val placeDao: PlaceDao
) : ViewModel(), LifecycleObserver {
    private val _placeName = MutableLiveData<String>()
    val placeName: LiveData<String>
        get() = _placeName

    fun onPlaceName(placeName: String) = viewModelScope.launch {
        _placeName.value = placeName
    }
}

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryDao: CategoryDao
) : ViewModel(), LifecycleObserver {
    val categories = categoryDao.getAll().asLiveData()
    private val _selectedCategory = MutableLiveData<Category>()
    val selectedCategory: LiveData<Category>
        get() = _selectedCategory

    fun onSelectedCategory(category: Category) = viewModelScope.launch {
        _selectedCategory.value = category
    }
}

@HiltViewModel
class ViewPagerViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val max = 100
    private val _currentPage = MutableLiveData<String>()
    val currentPage: LiveData<String>
        get() = _currentPage

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progress

    fun onChangePage(page: Int, total: Int) = viewModelScope.launch {
        _currentPage.value = "$page/$total"
        _progress.value = page * (max / total)
    }
}

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val placeDao: PlaceDao,
    private val categoryDao: CategoryDao
) : ViewModel(), LifecycleObserver {
    private val _isCheckIn = MutableLiveData<Boolean>()
    val isCheckIn: LiveData<Boolean>
        get() = _isCheckIn

    fun onCheckIn() = viewModelScope.launch {
        _isCheckIn.value = true
    }

    fun onCheckOut() = viewModelScope.launch {
        _isCheckIn.value = false
    }
}
