package com.deo.sticky.features.checkin.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.deo.sticky.features.category.ui.CategoryFragment
import com.deo.sticky.features.place.ui.PlaceFragment

const val NUM_PAGES = 2

class FragmentViewPager(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlaceFragment()
            1 -> CategoryFragment()
            else -> throw IllegalArgumentException()
        }
    }
}
