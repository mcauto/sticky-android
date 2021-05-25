package com.deo.sticky.features.checkin.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.deo.sticky.R
import com.deo.sticky.databinding.FragmentCheckInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckInFragment : Fragment(R.layout.fragment_check_in) {
    lateinit var binding: FragmentCheckInBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckInBinding.bind(view)
        initBack(binding)
        initViewPager(binding)
    }

    fun next() {
        binding.viewPager.apply { setCurrentItem(currentItem + 1, true) }
    }

    private fun initBack(binding: FragmentCheckInBinding) {
        val viewPager = binding.viewPager
        binding.back.setOnClickListener { view ->
            if (viewPager.currentItem == 0) {
                view.findNavController().popBackStack()
            } else {
                viewPager.setCurrentItem(viewPager.currentItem - 1, true)
            }
        }
    }

    private fun initViewPager(binding: FragmentCheckInBinding) {
        binding.progress.apply {
            progress = (binding.viewPager.currentItem + 1) * (max / NUM_PAGES)
        }

        val callback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                binding.progress.apply {
                    progress = (binding.viewPager.currentItem + 1) * (max / NUM_PAGES)
                }
                super.onPageScrollStateChanged(state)
            }
        }
        binding.viewPager.apply {
            isUserInputEnabled = false
            adapter = FragmentViewPager(this@CheckInFragment)
            registerOnPageChangeCallback(callback)
        }
    }
}
