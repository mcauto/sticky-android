package com.deo.sticky.features.checkin

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
    private val _placeName = MutableLiveData("")
    val placeName: LiveData<String>
        get() = _placeName

    fun onPlaceName(placeName: String) = viewModelScope.launch {
        _placeName.value = placeName
    }

    fun initialize() = viewModelScope.launch {
        _placeName.value = ""
    }
}

@HiltViewModel
class CategoryViewModel @Inject constructor(
    categoryDao: CategoryDao
) : ViewModel(), LifecycleObserver {
    val categories = categoryDao.getAll().asLiveData()
    private val _selectedCategory = MutableLiveData<Category>()
    val selectedCategory: LiveData<Category>
        get() = _selectedCategory

    fun onSelectedCategory(category: Category) = viewModelScope.launch {
        _selectedCategory.value = category
    }

    fun initialize() = viewModelScope.launch {
        _selectedCategory.value = Category()
    }
}

@HiltViewModel
class ViewPagerViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val max = 100
    val totalPage = 2

    private val _pageDescription = MutableLiveData<String>()
    val pageDescription: LiveData<String>
        get() = _pageDescription

    private val _currentPage = MutableLiveData(1)
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progress

    fun onChangePage(page: Int) = viewModelScope.launch {
        _currentPage.value = page
        _pageDescription.value = "$page/$totalPage"
        _progress.value = page * (max / totalPage)
    }

    fun onClickNext() = viewModelScope.launch {
        val nextPage = _currentPage.value!!.plus(1)
        onChangePage(nextPage)
    }

    fun onClickBack() = viewModelScope.launch {
        val previousPage = _currentPage.value!!.minus(1)
        onChangePage(previousPage)
    }

    fun initialize() {
        onChangePage(1)
    }
}

@HiltViewModel
class CheckInViewModel @Inject constructor(
    placeDao: PlaceDao,
    categoryDao: CategoryDao
) : ViewModel(), LifecycleObserver {
    private val _isCheckIn = MutableLiveData(false)
    val isCheckIn: LiveData<Boolean>
        get() = _isCheckIn

    fun onCheckIn() = viewModelScope.launch {
        _isCheckIn.value = true
    }

    fun onCheckOut() = viewModelScope.launch {
        _isCheckIn.value = false
    }
}
