package com.deo.sticky.features.checkin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.deo.sticky.R
import com.deo.sticky.base.BindableFragment
import com.deo.sticky.databinding.FragmentCheckInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
internal class CheckInFragment :
    BindableFragment<FragmentCheckInBinding>(R.layout.fragment_check_in) {
    private val viewPagerViewModel: ViewPagerViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val placeViewModel: PlaceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewPagerViewModel = this.viewPagerViewModel
        }
        initBack(binding)
        initViewPager(binding)
    }

    fun next() {
        binding.viewPager.apply { setCurrentItem(currentItem + 1, true) }
    }

    private fun initBack(binding: FragmentCheckInBinding) {
        val viewPager = binding.viewPager
        binding.back.setOnClickListener {
            if (viewPager.currentItem == 0) {
                it.findNavController().popBackStack()
            } else {
                viewPager.setCurrentItem(viewPager.currentItem - 1, true)
            }
        }
    }

    private fun initViewPager(binding: FragmentCheckInBinding) {
        binding.progress.apply {
            viewPagerViewModel.onChangePage(1, NUM_PAGES)
        }
        binding.viewPager.apply {
            isUserInputEnabled = false
            adapter = FragmentViewPager(
                this@CheckInFragment,
                categoryViewModel,
                placeViewModel
            )
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                @SuppressLint("SetTextI18n")
                override fun onPageScrollStateChanged(state: Int) {
                    val page = binding.viewPager.currentItem + 1
                    viewPagerViewModel.onChangePage(page, NUM_PAGES)
                    super.onPageScrollStateChanged(state)
                }
            })
        }
    }
}
