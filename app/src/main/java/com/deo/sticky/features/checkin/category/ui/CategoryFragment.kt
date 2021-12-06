package com.deo.sticky.features.checkin.category.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deo.sticky.R
import com.deo.sticky.base.BindableFragment
import com.deo.sticky.databinding.FragmentCheckInCategoryBinding
import com.deo.sticky.features.checkin.CategoryViewModel
import com.deo.sticky.features.checkin.CheckInViewModel
import com.deo.sticky.features.checkin.PlaceViewModel
import com.deo.sticky.features.checkin.category.models.Category
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@AndroidEntryPoint
@ExperimentalCoroutinesApi
internal class CategoryFragment :
    BindableFragment<FragmentCheckInCategoryBinding>(R.layout.fragment_check_in_category) {
    private val _categoryViewModel: CategoryViewModel by activityViewModels()
    private val _checkInViewModel: CheckInViewModel by activityViewModels()
    private val _placeViewModel: PlaceViewModel by activityViewModels()
    private val listener = object : CategoryAdapter.OnItemClickListener {
        override fun onItemClicked(category: Category) {
            _categoryViewModel.onSelectedCategory(category)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryAdapter = CategoryAdapter(listener)
        binding.apply {
            categoryViewModel = _categoryViewModel
            recycler.apply {
                adapter = categoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            start.setOnClickListener {
                val placeName = _placeViewModel.placeName.value
                val latitude = _placeViewModel.latitude.value
                val longitude = _placeViewModel.longitude.value
                val categoryId = _categoryViewModel.selectedCategory.value?.id
                if (placeName == null ||
                    latitude == null ||
                    longitude == null ||
                    categoryId == null
                ) {
                    Timber.e("필수 정보가 없습니다.")
                } else {
                    _checkInViewModel.onCheckIn(
                        placeName = placeName,
                        longitude = longitude,
                        latitude = latitude,
                        categoryId = categoryId
                    )
                }
            }
        }

        _categoryViewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }
    }
}
