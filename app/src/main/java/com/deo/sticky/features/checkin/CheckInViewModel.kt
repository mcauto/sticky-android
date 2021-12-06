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
import com.deo.sticky.features.checkin.place.models.entity.PlaceWithDistance
import com.deo.sticky.features.checkout.models.AccumulateBase
import com.deo.sticky.features.checkout.models.AccumulateDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class PlaceViewModel @Inject constructor(
    val placeDao: PlaceDao
) : ViewModel(), LifecycleObserver {
    private val _places = MutableLiveData<List<PlaceWithDistance>>()
    val places: LiveData<List<PlaceWithDistance>>
        get() = _places

    private val _placeName = MutableLiveData("")
    val placeName: LiveData<String>
        get() = _placeName

    private val _latitude = MutableLiveData<Double>(GANGNAM_COFFEE_BEAN_LATITUDE)
    val latitude: LiveData<Double>
        get() = _latitude

    private val _longitude = MutableLiveData<Double>(GANGNAM_COFFEE_BEAN_LONGITUDE)
    val longitude: LiveData<Double>
        get() = _longitude

    fun getPlacesWithRadius(
        latitude: Double,
        longitude: Double,
        radius: Int = 500
    ) = viewModelScope.launch {
        _places.value = placeDao.getPlacesWithinRadius(
            latitude = latitude,
            longitude = longitude,
            radius = radius
        )
        Timber.d("places: ${places.value}")
    }

    fun onPlaceName(placeName: String) = viewModelScope.launch {
        _placeName.value = placeName
    }

    fun onLatitude(latitude: Double) = viewModelScope.launch {
        _latitude.value = latitude
    }

    fun onLongitude(longitude: Double) = viewModelScope.launch {
        _longitude.value = longitude
    }

    fun initialize() = viewModelScope.launch {
        _placeName.value = ""
        _latitude.value = GANGNAM_COFFEE_BEAN_LATITUDE
        _longitude.value = GANGNAM_COFFEE_BEAN_LONGITUDE
    }

    companion object {
        private const val GANGNAM_COFFEE_BEAN_LATITUDE = 37.4988373
        private const val GANGNAM_COFFEE_BEAN_LONGITUDE = 127.0292686
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
    val placeDao: PlaceDao,
    val categoryDao: CategoryDao,
    val accumulateDao: AccumulateDao
) : ViewModel(), LifecycleObserver {
    private val _isCheckIn = MutableLiveData(false)
    val isCheckIn: LiveData<Boolean>
        get() = _isCheckIn

    private var _accumulateId: Long = 0
    private lateinit var job: Job
    private var interval: Long = 1000
    private val _start = MutableLiveData(0L)
    val start: LiveData<Long>
        get() = _start
    private val _hhmmss = MutableLiveData("")
    val hhmmss: LiveData<String>
        get() = _hhmmss

    fun onCheckIn(
        placeName: String,
        longitude: Double,
        latitude: Double,
        categoryId: Long
    ) = viewModelScope.launch {
        _isCheckIn.value = true
        _accumulateId = accumulateDao.start(
            AccumulateBase(
                placeDao.add(
                    name = placeName,
                    longitude = longitude,
                    latitude = latitude
                ),
                categoryId
            )
        )
    }

    fun onCheckOut() = viewModelScope.launch {
        _isCheckIn.value = false
        accumulateDao.end(_accumulateId)
    }

    fun timerStart(fnCallback: () -> Unit) {
        if (::job.isInitialized) job.cancel()

        job = viewModelScope.launch {
            while (true) {
                delay(interval)
                _start.value = _start.value?.inc()
                val seconds = _start.value ?: 0
                _hhmmss.value = String.format(
                    "%02d : %02d : %02d", seconds / 3600,
                    (seconds % 3600) / 60, (seconds % 60)
                )
                fnCallback()
            }
        }
    }

    fun timerStop() {
        if (::job.isInitialized) job.cancel()
    }
}
