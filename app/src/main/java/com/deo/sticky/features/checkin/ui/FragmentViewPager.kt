package com.deo.sticky.features.checkin.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.deo.sticky.features.category.ui.CategoryFragment
import com.deo.sticky.features.place.ui.PlaceFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val NUM_PAGES = 2

@ExperimentalCoroutinesApi
class FragmentViewPager constructor(
    parent: Fragment,
    private val viewModel: CheckInViewModel,
) :
    FragmentStateAdapter(parent) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlaceFragment(viewModel)
            1 -> CategoryFragment(viewModel)
            else -> throw IllegalArgumentException()
        }
    }
}
