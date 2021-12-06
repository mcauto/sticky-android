package com.deo.sticky.features.checkin

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.deo.sticky.features.checkin.category.ui.CategoryFragment
import com.deo.sticky.features.checkin.place.ui.PlaceFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FragmentViewPager constructor(
    parent: Fragment,
    private val totalPage: Int
) : FragmentStateAdapter(parent) {
    override fun getItemCount(): Int = totalPage

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlaceFragment()
            1 -> CategoryFragment()
            else -> throw IllegalArgumentException()
        }
    }
}
