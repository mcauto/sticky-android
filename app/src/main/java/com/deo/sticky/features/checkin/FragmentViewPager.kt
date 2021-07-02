package com.deo.sticky.features.checkin

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.deo.sticky.features.checkin.category.ui.CategoryFragment
import com.deo.sticky.features.checkin.place.ui.PlaceFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NUM_PAGES = 2

@ExperimentalCoroutinesApi
class FragmentViewPager constructor(
    parent: Fragment,
    private val categoryViewModel: CategoryViewModel,
    private val placeViewModel: PlaceViewModel
) :
    FragmentStateAdapter(parent) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlaceFragment(placeViewModel)
            1 -> CategoryFragment(categoryViewModel)
            else -> throw IllegalArgumentException()
        }
    }
}
