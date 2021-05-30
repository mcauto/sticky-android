package com.deo.sticky.features.checkin.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.deo.sticky.R
import com.deo.sticky.databinding.FragmentCheckInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CheckInFragment : Fragment(R.layout.fragment_check_in) {
    private val checkInViewModel: CheckInViewModel by viewModels()
    private lateinit var binding: FragmentCheckInBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckInBinding.bind(view)
        initBack(binding)
        initViewPager(binding)
        binding.apply {
            checkinViewModel = this.checkinViewModel
        }
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
            progress = (binding.viewPager.currentItem + 1) * (max / NUM_PAGES)
        }

        val callback = object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageScrollStateChanged(state: Int) {
                val page = binding.viewPager.currentItem + 1
                binding.progress.apply {
                    progress = page * (max / NUM_PAGES)
                }
                binding.progressLabel.text = "$page/$NUM_PAGES"
                super.onPageScrollStateChanged(state)
            }
        }
        binding.viewPager.apply {
            isUserInputEnabled = false
            adapter = FragmentViewPager(
                this@CheckInFragment, checkInViewModel
            )
            registerOnPageChangeCallback(callback)
        }
    }
}
