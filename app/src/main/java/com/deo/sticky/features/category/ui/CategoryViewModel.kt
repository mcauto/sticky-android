package com.deo.sticky.features.category.ui

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.deo.sticky.features.category.models.Category
import com.deo.sticky.features.category.models.CategoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryViewModel @Inject constructor(
    categoryDao: CategoryDao,
) : ViewModel(), LifecycleObserver {
    val categories = categoryDao.getAll().asLiveData()

    fun onCategorySelected(category: Category) = viewModelScope.launch {
        // 카테고리 checked
    }
}
